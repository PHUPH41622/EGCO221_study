package Ex8_012;

// Napasrapee Satittham 6513012

import java.io.File;
import java.util.*;

public class Newmain8 {
    public static void main(String[] args) {
        ActorMap AM = new ActorMap();
        int countMovie = 0, countName = 0;
        try {
            String file = "src/main/java/Ex8_012/movies.txt";
            Scanner filescan = new Scanner(new File(file));
            Scanner keyboard = new Scanner(System.in);
            for(int i = 0; filescan.hasNext(); i++) {
                String line = filescan.nextLine();
                String[] col = line.split(";");
                String movie = col[0];
                for (int j = 1; j < col.length; j++) {
                    String actor = col[j];
                    AM.addActor(actor, movie);
                }
                countMovie++;
            }
            System.out.println("Total movies = "+countMovie);
            System.out.println("Total actors = "+AM.getActorsCount()+"\n");
            AM.printworkingMap();

            while (true) {
                System.out.println();
                System.out.println("=".repeat(150));
                System.out.println("Find movies >> 0 = set initial actors,");
                System.out.println("               1 = contain actors,");
                System.out.println("               2 = without actors,");
                System.out.println("          others = quit");
                int mode = keyboard.nextInt();
                if (mode == 0) {
                    AM.initialActors();
                } else if (mode == 1) {
                    AM.containActors();
                } else if (mode == 2) {
                    AM.withoutActors();
                } else {
                    break;
                }
            }

        } catch (InputMismatchException e) {
            System.out.println("quit");
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}

class ActorMap {
    // Key = actor, Value = set of movies
    // Java's String already has consistent equals & compareTo for equality check

    private TreeMap<String, LinkedHashSet<String>> workingMap = new TreeMap<>();
    private LinkedHashSet<String> resultSet = new LinkedHashSet<>();

    public void addActor(String actor, String movie) {
        if (!workingMap.containsKey(actor)) {
            workingMap.put(actor, new LinkedHashSet<>());
        }
        workingMap.get(actor).add(movie);
    }
    public int getActorsCount() {
        return workingMap.size();
    }
    public void printworkingMap() {
        Set<String> KS = workingMap.keySet();
        for (String key : KS) {
            int x = 1;
            LinkedHashSet value = workingMap.get(key);
            System.out.printf("%20s  >>  ", key);
            for(Object v : value) {
                System.out.printf("%-30s", v);
                x++;
                if( x > 5 && v != null) {
                    System.out.print("\n                      >>  ");
                    x = 1;
                }
            }
            System.out.print("\n");
        }
    }

    public void initialActors() {
        Set<String> validActor = InputValidActor();
        Set<String> KS = workingMap.keySet();

        for (String actor : validActor) {
            int x = 1;
            if (KS.contains(actor)) {
                LinkedHashSet<String> movies = workingMap.get(actor);
                resultSet.addAll(movies);
                System.out.printf("%20s  >>  ", actor);
                for(String m : workingMap.get(actor)) {
                    System.out.printf("%-30s", m);
                    x++;
                    if( x > 5 && m != null) { System.out.print("\n                      >>  "); x = 1; }
                } System.out.print("\n");
            }
        }

        System.out.print("\nResult = "+resultSet+"\n\n");
        System.out.print("Total movies = "+resultSet.size()+"\n");
    }
    public void containActors() {
        Set<String> validActor = InputValidActor();
        Set<String> KS = workingMap.keySet();

        for (String actor : validActor) {
            int x = 1;
            if (KS.contains(actor)) {
                LinkedHashSet<String> movies = workingMap.get(actor);
                resultSet.retainAll(movies);
                System.out.printf("%20s  >>  ", actor);
                for(String m : workingMap.get(actor)) {
                    System.out.printf("%-30s", m);
                    x++;
                    if( x > 5 && m != null) { System.out.print("\n                      >>  "); x = 1; }
                } System.out.print("\n");
            }
        }

        System.out.print("\nResult = "+resultSet+"\n\n");
        System.out.print("Total movies = "+resultSet.size()+"\n");
    }

    public void withoutActors() {
        Set<String> validActor = InputValidActor();
        Set<String> KS = workingMap.keySet();

        for (String actor : validActor) {
            int x = 1;
            if (KS.contains(actor)) {
                LinkedHashSet<String> movies = workingMap.get(actor);
                resultSet.removeAll(movies);
                System.out.printf("%20s  >>  ", actor);
                for(String m : workingMap.get(actor)) {
                    System.out.printf("%-30s", m);
                    x++;
                    if( x > 5 && m != null) { System.out.print("\n                      >>  "); x = 1; }
                } System.out.print("\n");
            }
        }

        System.out.print("\nResult = "+resultSet+"\n\n");
        System.out.print("Total movies = "+resultSet.size()+"\n");
    }

    public LinkedHashSet<String> InputValidActor () {
        LinkedHashSet<String> validActors = new LinkedHashSet<>();
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Add names or surnames separated by comma = ");
        String fullstring = keyboard.nextLine();
        String[] substring = fullstring.split(",");
        Set<String> KS = workingMap.keySet();

        for (int i = 0; i < substring.length; i++) {
            for (String key : KS) {
                if(key.toLowerCase().contains(substring[i].trim())) {
                    validActors.add(key);
                }
            }
        }

        System.out.println("Valid input actors = "+validActors+"\n");
        return validActors;
    }
}