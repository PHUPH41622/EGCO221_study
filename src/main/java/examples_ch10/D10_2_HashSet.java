package examples_ch10;

import java.util.*;

class D10_2_HashSet {
    // Use method equals and hashcode in class Student
    HashSet<Student> HS1, HS2;
    
    public void initialize()
    {
	ArrayList<Student> A = new ArrayList<>();
	A.add( new Student("Ellen", 45.0) );	
	A.add( new Student("David", 35.0) );
	A.add( new Student("Carol", 45.0) );	
	A.add( new Student("Annie", 55.0) );
	A.add( new Student("Betty", 35.0) );
	A.add( new Student("Annie", 25.0) );    
        
        // nodes to HS1, HS2
	//HS1 = new HashSet<>();          // unpredictable order
	//HS2 = new HashSet<>();
        
        HS1 = new LinkedHashSet<>();      // follow input order
        HS2 = new LinkedHashSet<>();

        HS1.add(A.get(2)); HS1.add(A.get(4)); HS1.add(A.get(3)); HS1.add(A.get(0));
        HS2.add(A.get(2)); HS2.add(A.get(5)); HS2.add(A.get(1));
        
	System.out.println("\nHS1 "); printSet(HS1);
	System.out.println("\nHS2 "); printSet(HS2);
    }
    
    // support both TreeSet and HashSet
    public void printSet(Set<Student> S)   { System.out.println(S); }  
    
    public static void main(String[] args) {
        D10_2_HashSet mainprog = new D10_2_HashSet();
        mainprog.initialize();
        //mainprog.testEquality();
        
        // Uncomment both to compare results from HashSet and TreeSet
        // (1) Try name as natural order (consistent with equals)
        // (2) Try score as natural order (inconsistent with equals)
        
        mainprog.setOperationsByHash();
        mainprog.setOperationsByTree();
    }

    public void testEquality() {
		Scanner scan = new Scanner(System.in);
		System.out.println("\nEnter Student name  : ");
		String name = scan.next();
		Student key = new Student(name, 0);
		System.out.printf("HS1 contains %s ? %b \n", key, HS1.contains(key));
		System.out.printf("HS2 contains %s ? %b \n", key, HS2.contains(key));
    }

    public void setOperationsByHash() {
        // Equality is based on equals --> (Annie, 55) = (Annie, 25)        
        
        System.out.println("\n" + "=".repeat(40));
                
        LinkedHashSet<Student> union = new LinkedHashSet<>(HS1);
		union.addAll(HS2);
		System.out.println("Union by Hash ");
		printSet( union );

		LinkedHashSet<Student> intersect = new LinkedHashSet<>(HS1);
		intersect.retainAll(HS2);
		System.out.println("\nIntersect by Hash ");
		printSet( intersect );

		LinkedHashSet<Student> diff = new LinkedHashSet<>(HS1);
		diff.removeAll(HS2);
		System.out.println("\nHS1 - HS2 by Hash ");
		printSet( diff );
    }
    
    public void setOperationsByTree() {
        // === Natural order by score ===
        // - Duplication when copying from HS1 --> (Carol, 45) = (Ellen, 45)         
        // - Duplication when using addAll     --> (Betty, 35) = (David, 35)
        
        System.out.println("\n" + "+".repeat(40));        

        TreeSet<Student> union = new TreeSet<>(HS1);
		union.addAll(HS2);
		System.out.println("Union by Tree, based on compareTo ");
		printSet( union );

        TreeSet<Student> intersect = new TreeSet<>(HS1);
		intersect.retainAll(HS2);
		System.out.println("\nIntersect by Tree, based on equals");
		printSet( intersect );

        TreeSet<Student> diff = new TreeSet<>(HS1);
		diff.removeAll(HS2);
		System.out.println("\nHS1 - HS2 by Tree, based on equals");
		printSet( diff );
    }    
}
