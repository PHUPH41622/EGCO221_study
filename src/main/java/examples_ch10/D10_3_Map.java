package examples_ch10;

import java.util.*;

class D10_3_Map 
{
    // use method equals and hashcode in class Student
    HashMap<String, Student> HM;
    TreeMap<String, Student> TM;
    
    public void initializeHash()
    {
        // Key equality is defined by method equals in class String        
	//HM = new HashMap<>();
        HM = new LinkedHashMap<>();
        
	HM.put("0002", new Student("Ellen", 45.0));	
	HM.put("0004", new Student("David", 35.0));
	HM.put("0006", new Student("Carol", 45.0));	
	HM.put("0001", new Student("Annie", 55.0));
	HM.put("0003", new Student("Betty", 35.0));
	HM.put("0004", new Student("Annie", 25.0));	// replace ("David", 35.0)	
        
	System.out.println("=== HashMap content ==="); 
        printMap(HM); 
    }
    
    public void initializeTreeFromHash()
    {
        // Key equality is defined by method compareTo in class String
        TM = new TreeMap<>(HM);
	System.out.println("\n=== TreeMap content ==="); 
        printMap(TM);        
    }
    
    ////////////////////////////////////////////////////////////////////////////
    public void printMap( Map<String, Student> M )
    {
        // (1) Use keys to retrieve value
        /*
            Set<String> KS = M.keySet();
            for (String key : KS)
            {
                    Student value = M.get(key);
                    System.out.printf("Key = %s, value = %s \n", key, value);
            }
        */

        
        // (2) Retrieve the whole entry
        /*
        Set< Map.Entry<String, Student> > ES = M.entrySet();
        for( Map.Entry<String, Student> entry : ES )
        {
            String key    = entry.getKey();
            Student value = entry.getValue();
            System.out.printf("Key = %s, value = %s \n", key, value);
        }
        */
        
        
        // (3) Implicit toString : notice enclosing symbols
        //     - Map's toString outputs members in set {...}
        //     - Entry's toString outputs members in list [...]
        System.out.println("Map toString \n" + M);                 
        System.out.println("\nMap.Entry toString \n" + M.entrySet() );       
    }    
    
    ////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        D10_3_Map mainprog = new D10_3_Map();
        mainprog.initializeHash();  
        //mainprog.testHashMap();
        
        //mainprog.initializeTreeFromHash();
        //mainprog.testTreeMap();

        
        // Uncomment all above lines to avoid confusion
        mainprog.testNewTree();
    }
        
    ////////////////////////////////////////////////////////////////////////////
    public void testHashMap() {
        checkContent(HM);              // get(key) is based on equals
    }
    
    public void testTreeMap() {
        checkContent(TM);              // get(key) is based on compareTo/compare 
        //nodeRetrieval();
        //submapRetrieval();
    }
        
    public void checkContent( Map<String, Student> M ) {
        Scanner scan = new Scanner(System.in);
        System.out.println("\nEnter Student ID   : "); 
	    String id = scan.next();

        if (M.containsKey(id)) M.get(id).print();
        else System.out.printf("%s doesn't exist\n", id);
        
        System.out.println("\nEnter Student name : "); 
        String name = scan.next();
        Student value = new Student(name, 0);
        System.out.printf("Contains %s ? %b \n", value, M.containsValue(value));
    }
    
    ////////////////////////////////////////////////////////////////////////////
    public void nodeRetrieval() {
        Scanner scan = new Scanner(System.in);
        System.out.println();
        System.out.println("First node = " + TM.firstEntry() );
        System.out.println("Last  node = " + TM.get(TM.lastKey()) );
        
        System.out.println("\nEnter ID : "); String id = scan.next();
        System.out.printf("Left  of %s = %s \n", id, TM.lowerEntry(id) );
        System.out.printf("Right of %s = %s \n", id, TM.get(TM.higherKey(id)) );
    }
    
    public void submapRetrieval() {

        // (1) Try id1 = 0003; id2 = 0006
        // (2) Try id1 = 0006; id2 = 0003
        // (3) Try id1 = 0003; id2 = 0005
        // (4) Try id1 = 0006; id2 = 0007   
        
        Scanner scan = new Scanner(System.in);
        System.out.println("\nEnter ID 1 : "); String id1 = scan.next();
        Map<String, Student> LM = TM.headMap(id1);
        System.out.printf("\nLeft submap of %s \n", id1);
        printMap( LM );
		
        Map<String, Student> RM = TM.tailMap(id1);
        System.out.printf("\nRight submap of %s \n", id1);
        printMap( RM );
        
	    System.out.println("\nEnter ID 2 : "); String id2 = scan.next();
        try {
            Map<String, Student> SM = TM.subMap(id1, id2);
            System.out.printf("\nSubmap from %s to %s \n", id1, id2); 
            printMap( SM );  
        }
        catch(Exception e) { System.out.println(e); }
    }

    ////////////////////////////////////////////////////////////////////////////
    public void testNewTree() {
        // Key equality is based on length
        
        class SortStringByLength implements Comparator<String> {
            public int compare(String s1, String s2) { return s1.length()-s2.length(); }
        }

        TM = new TreeMap<>(new SortStringByLength());
        TM.put("2",    new Student("Ellen", 45.0));
        TM.put("04",   new Student("David", 35.0));
        TM.put("006",  new Student("Carol", 45.0));
        TM.put("0001", new Student("Annie", 55.0));
        TM.put("0003", new Student("Betty", 35.0));    // replace ("Annie", 55.0)
        TM.put("4",    new Student("Annie", 25.0));    // replace ("Ellen", 45.0)
       
        printMap(TM);
        checkContent(TM);
    }
}
