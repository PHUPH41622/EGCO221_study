package Ex3_012;

//Napasrapee Satittham + 6513012

import java.io.File;
import java.util.*;

public class Newmain3 {
    public static void main(String[] args) {
        Newmain3 mainapp = new Newmain3();
        String path = "src/main/java/Ex3_012/";
        String fileName = "languages.txt";
        ArrayList<Language> AL = new ArrayList<>();
        Scanner keyboardScan = new Scanner(System.in);
        boolean running = true;
        try {
            Scanner filescan = new Scanner(new File(path + fileName));

            for (int i = 0; filescan.hasNext(); i++) {
                String line = filescan.nextLine();
                String[] col = line.split(",");
                if (i == 0) {
                    continue;
                }
                Language l = new Language(col[0].trim(), Integer.parseInt(col[1].trim()), Double.parseDouble(col[2].trim()), Double.parseDouble(col[3].trim()), Double.parseDouble(col[4].trim()));
                AL.add(l);
            }
        } catch (Exception e) {
            System.out.print(e + " --> ");
            System.out.println("New file name = ");
            fileName = keyboardScan.next();
        }
        while(running) {
            System.out.println("Sort by >> n = name");
            System.out.println("           y = year");
            System.out.println("           q = Stackoverflow question");
            System.out.println("           r = GitHub repos");
            System.out.println("           s = GitHub stars");
            System.out.println("      others = quit");
            String mode = (keyboardScan.next()).toLowerCase();

            switch (mode) {
                case "n":
                    System.out.println("Language          Release Year    Stackoverflow Question(M)    GitHub Repos(M)   GitHub Top Stars(K)");
                    System.out.println("========================================================================================================");
                    AL.sort(new SortLanguageByName());
                    for (Language l : AL) l.print();
                    break;
                case "y":
                    System.out.println("Language          Release Year    Stackoverflow Question(M)    GitHub Repos(M)   GitHub Top Stars(K)");
                    System.out.println("========================================================================================================");
                    mainapp.Ysort(AL);
                    break;
                case "q":
                    System.out.println("Language          Release Year    Stackoverflow Question(M)    GitHub Repos(M)   GitHub Top Stars(K)");
                    System.out.println("========================================================================================================");
                    mainapp.Qsort(AL);
                    break;
                case "r":
                    System.out.println("Language          Release Year    Stackoverflow Question(M)    GitHub Repos(M)   GitHub Top Stars(K)");
                    System.out.println("========================================================================================================");
                    mainapp.Rsort(AL);
                    break;
                case "s":
                    System.out.println("Language          Release Year    Stackoverflow Question(M)    GitHub Repos(M)   GitHub Top Stars(K)");
                    System.out.println("========================================================================================================");
                    mainapp.Ssort(AL);
                    break;
                default:
                    System.out.println("A problem was encountered !!");
                    running = false;
            }
        }
    }

    public void Ysort(ArrayList<Language> AL) {
        Collections.sort(AL, new SortLanguageByYear()
                .thenComparing( new SortLanguageByStackoverflow().reversed())
                .thenComparing( new SortLanguageByGithubRepos().reversed())
                .thenComparing( new SortLanguageByGithubStars().reversed())
                .thenComparing( new SortLanguageByName())
        );
        for (Language l : AL) l.print();
    }

    public void Qsort(ArrayList<Language> AL) {
        Collections.sort(AL, new SortLanguageByStackoverflow().reversed()
                .thenComparing( new SortLanguageByGithubRepos())
                .thenComparing( new SortLanguageByGithubStars())
                .thenComparing( new SortLanguageByName())
        );
        for (Language l : AL) l.print();
    }

    public void Rsort(ArrayList<Language> AL) {
        Collections.sort(AL, new SortLanguageByGithubRepos().reversed()
                .thenComparing( new SortLanguageByGithubStars())
                .thenComparing( new SortLanguageByName())
        );
        for (Language l : AL) l.print();
    }

    public void Ssort(ArrayList<Language> AL) {
        Collections.sort(AL, new SortLanguageByGithubStars().reversed()
                .thenComparing( new SortLanguageByName())
        );
        for (Language l : AL) l.print();
    }
}

class Language {
    private String name;
    private int releaseYear;
    private double stackQuestions; // Stackoverflow questions in millions
    private double githubRepos; // GitHub repositories in millions
    private double githubTopStars; // GitHub top-repo stars in thousands

    public Language(String name, int year, double question, double repo, double star) {
        this.name = name;
        releaseYear = year;
        stackQuestions = question;
        githubRepos = repo;
        githubTopStars = star;
    }

    public String getName()              { return name; }
    public int    getReleaseYear()       { return releaseYear; }
    public double getStackQuestions()    { return stackQuestions; }
    public double getGithubRepos()       { return githubRepos; }
    public double getGithubTopStars()    { return githubTopStars; }

    public void print() {
        System.out.printf("%-22s%-22d%-23.1f%-20.1f%.1f\n", this.name, releaseYear, stackQuestions, githubRepos, githubTopStars);
    }
}

class SortLanguageByName implements Comparator<Language> {
    public int compare(Language l1, Language l2)      { return l1.getName().compareToIgnoreCase(l2.getName()); }
}
class SortLanguageByYear implements Comparator<Language> {
    public int compare(Language l1, Language l2)      { return l1.getReleaseYear() - l2.getReleaseYear(); }
}
class SortLanguageByStackoverflow implements Comparator<Language> {
    public int compare(Language l1, Language l2)      { return Double.compare(l1.getStackQuestions(), l2.getStackQuestions()); }
}
class SortLanguageByGithubRepos implements Comparator<Language> {
    public int compare(Language l1, Language l2)      { return Double.compare(l1.getGithubRepos(), l2.getGithubRepos()); }
}
class SortLanguageByGithubStars implements Comparator<Language> {
    public int compare(Language l1, Language l2)      { return Double.compare(l1.getGithubTopStars(), l2.getGithubTopStars()); }
}
