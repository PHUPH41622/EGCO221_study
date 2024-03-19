package examples_ch10;

import java.util.*;

class D10_1_TreeSet {
    // No need to use methods equals and hashcode in class Student
    TreeSet<Student> TS;
    
    public void initialize() {
        //Exception!! if Student doesn't implement Comparable
//        TS = new TreeSet<>(); //this is exception
        
//        TS = new TreeSet<>( new SortStudentByScore() );
        TS = new TreeSet<>( new SortStudentByScore().thenComparing(new SortStudentByName()) );
        
        TS.add( new Student("Ellen", 45.0) );
        TS.add( new Student("David", 35.0) );
        TS.add( new Student("Carol", 45.0) );	// equal Ellen if sorting by score only
        TS.add( new Student("Annie", 55.0) );
        TS.add( new Student("Betty", 35.0) );   // equal David if sorting by score only
        TS.add( new Student("Annie", 25.0) );
        
        System.out.println("\nOriginal Content"); 
        printSet(TS);
    }
    
    // support both TreeSet and HashSet
    public void printSet(Set<Student> S) {
        //for (Student s : S) s.print(); 
        System.out.println(S);
    }
    
    public static void main(String[] args) {
        D10_1_TreeSet mainprog = new D10_1_TreeSet();
        mainprog.initialize();
//        mainprog.testEquality();
//        mainprog.nodeRetrieval();
        mainprog.subtreeRetrieval();
    }
		
    ////////////////////////////////////////////////////////////////////////////
    public void testEquality() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\nEnter Student name  : ");
        String name = scan.next();
        System.out.println("Enter Student score : ");
        double score = scan.nextDouble();
        Student key = new Student(name, score);

        System.out.printf("Contain %s ? %b \n", key, TS.contains(key));
        System.out.printf("Remove  %s ? %b \n", key, TS.remove(key));
        System.out.println("\nNew Content"); printSet(TS);
    }

    ////////////////////////////////////////////////////////////////////////////
    public void nodeRetrieval() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\nEnter Student name  : ");
        String name = scan.next();
        System.out.println("Enter Student score : ");
        double score = scan.nextDouble();
        Student key = new Student(name, score);

            // (1) Try key = (betty, 35)
            // (2) Try key = (annie, 25) or (annie, 55)
            // (3) Try key = (freddy, 35)

        Student s1 = TS.lower(key);
        System.out.printf("\nLeft  of %s = ", key);
            System.out.println(s1);
        if (s1 != null)            // be careful about null pointer
                s1.print();            // when using s1 to call method

        Student s2 = TS.higher(key);
        System.out.printf("\nRight of %s = %s \n", key, s2);
    }

    public void subtreeRetrieval() {
        // (1) Try key1 = (betty, 35);  key2 = (ellen, 45)
        // (2) Try key1 = (ellen, 45);  key2 = (betty, 35)
        // (3) Try key1 = (freddy, 35); key2 = (ellen, 45)
        // (4) Try key1 = (freddy, 0);  key2 = (annie, 25)        
        
        Scanner scan = new Scanner(System.in);
        System.out.println("\nEnter Student name  1 : ");
        String name = scan.next();
        System.out.println("Enter Student score 1 : ");
        double score = scan.nextDouble();
        Student key1 = new Student(name, score);

        Set<Student> LTS = TS.headSet(key1);
        System.out.printf("\nLeft subtree of %s \n", key1);
        printSet( LTS );

        Set<Student> RTS = TS.tailSet(key1);
        System.out.printf("\nRight subtree of %s \n", key1);
        printSet( RTS );

        System.out.println("\nEnter Student name  2 : ");
        name = scan.next();
        System.out.println("Enter Student score 2 : ");
        score = scan.nextDouble();
        Student key2 = new Student(name, score);

        
        // Try case (2), with and without this condition
        if (checkValidSubset(key1, key2)) { // check or make sure that key1 < key2
            Set<Student> SS = TS.subSet(key1, key2);
            System.out.printf("\nSubtree from %s to %s \n", key1, key2);
            printSet( SS );            
        }
        
        
        /*
        try
        {
            Set<Student> SS = TS.subSet(key1, key2);
            System.out.printf("\nSubtree from %s to %s \n", key1, key2);
            printSet( SS );               
        }
        catch(Exception e) { System.out.println(e); }
        */
    }
    
    private boolean checkValidSubset(Student key1, Student key2) {
        boolean valid = false;
        if (TS.comparator() != null)        // tree is built by Comparator
        {
            if (TS.comparator().compare(key1, key2) <= 0)
                valid = true;
        }
        else                                // tree is built by natural order
        {
            if (key1.compareTo(key2) <= 0)
                valid = true;
        }
        return valid;                
    }
}
