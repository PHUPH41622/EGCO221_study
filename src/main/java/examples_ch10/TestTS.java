package examples_ch10;

import java.util.*;

public class TestTS {
    public static void main(String[] args) {
        TreeSet<Integer> ts = new TreeSet<>();

        ts.add(3);
        ts.add(4);
        ts.add(5);
        ts.add(3);

//        ts.pollLast();

        for(Integer i : ts) {
            System.out.println(i);
        }

        System.out.println(ts.last());
    }
}
