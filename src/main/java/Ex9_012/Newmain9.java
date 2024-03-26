package Ex9_012;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.alg.color.LargestDegreeFirstColoring;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.io.File;
import java.util.*;

//Napasrapee Satittham 6513012

public class Newmain9 {
    public static void main(String[] args) {
        ActorGraph AG = new ActorGraph();
        try {
            String file = "src/main/java/Ex9_012/movies.txt";
            Scanner filescan = new Scanner(new File(file));
            for(int i = 0; filescan.hasNext(); i++) {
                String line = filescan.nextLine();
                String[] col = line.split(";");
                String movie = col[0];
                for (int j = 1; j < col.length; j++) {
                    String actor = col[j];
                    AG.addActor(actor, movie);
                }
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        AG.initialize();
    }
}


class ActorGraph {
    public static final String BACON = "Kevin Bacon";
    private Graph<String, DefaultEdge> costarGraph;
    private Graph<String, DefaultEdge> conflictGraph;
    private TreeMap<String, LinkedHashSet<String>> workingMap = new TreeMap<>();
    GreedyColoring<String, DefaultEdge> color;
    List<Set<String>> colorList;

    public void initialize() {
        try {
            costarGraph = new SimpleGraph<>(DefaultEdge.class);
            conflictGraph = new SimpleGraph<>(DefaultEdge.class);

            ArrayList<String> node = new ArrayList<>(workingMap.keySet());

            Graphs.addAllVertices(costarGraph, node);
            Graphs.addAllVertices(conflictGraph, node);

            for (String actor : workingMap.keySet()) {
                LinkedHashSet<String> movies = workingMap.get(actor);
                for (String movie : movies) {
                    for (String coActor : workingMap.keySet()) {
                        if (!coActor.equals(actor) && workingMap.get(coActor).contains(movie)) {
                            costarGraph.addEdge(actor, coActor);
                        }
                    }
                }
            }

            Graphs.addGraph(conflictGraph, costarGraph);
            conflictGraph.removeVertex(BACON);

            baconParties();
            while (true) {
                baconDegree();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void addActor(String actor, String movie) {
        if (!workingMap.containsKey(actor)) {
            workingMap.put(actor, new LinkedHashSet<>());
        }
        workingMap.get(actor).add(movie);
    }

    public LinkedHashSet<String> InputValidActor () {
        LinkedHashSet<String> validActors = new LinkedHashSet<>();
        Scanner keyboard = new Scanner(System.in);
        System.out.println("\n==================== Bacon parties ====================");
        System.out.println("Enter names or surnames, or 0 to quit");
        String substring = keyboard.nextLine();
        if (Objects.equals(substring, "0")) {
            System.exit(0);
        }
        Set<String> KS = workingMap.keySet();

        for (String key : KS) {
            if(key.toLowerCase().contains(substring)) {
                validActors.add(key);
            }
        }

        System.out.println("Valid actors = "+validActors);
        return validActors;
    }
    public void baconDegree() {
        LinkedHashSet<String> validActor = InputValidActor();
        ShortestPathAlgorithm<String, DefaultEdge> shpath = new BFSShortestPath<>(costarGraph);
        for (String v : validActor) {
            GraphPath<String, DefaultEdge> gpath = shpath.getPath(v, BACON);
            List<String> allNodes = gpath.getVertexList();
            System.out.println("\n"+v+"  >>  bacon degree = "+(allNodes.size()-1));
            for (int i = 0; i < allNodes.size(); i++) {
                if(i < allNodes.size()-1) {
                    Set<String> moviesActor1 = workingMap.get(allNodes.get(i));
                    Set<String> moviesActor2 = workingMap.get(allNodes.get(i+1));
                    Set<String> sharedMovies = new HashSet<>(moviesActor1);
                    sharedMovies.retainAll(moviesActor2);
                    System.out.printf("%30s - %-30s", allNodes.get(i), allNodes.get(i+1));
                    System.out.println(sharedMovies);
                }
            }
        }
    }
    public void baconParties() {
        color = new LargestDegreeFirstColoring<>(conflictGraph);
        colorList = color.getColoring().getColorClasses();

        System.out.println("==================== Bacon parties ====================");
        System.out.println("By LargestDegreeFirstColoring  >>  total parties = "+colorList.size()+"\n");
        for (int i = 0; i < colorList.size(); i++) {
            List<String> sortedColor = new ArrayList<>(colorList.get(i));
            Collections.sort(sortedColor);
            int count = 0;
            System.out.printf("Parties %d  >>  guests = %s\n", i, sortedColor.size());
            for (int j = 0; j < sortedColor.size(); j++) {
                System.out.printf("%-30s", sortedColor.get(j));
                count++;
                if (count == 5) {
                    System.out.printf("\n");
                    count = 0;
                }
            }
            if (count != 0) {
                System.out.println();
            }
            System.out.println("\n");
        }
    }
}
