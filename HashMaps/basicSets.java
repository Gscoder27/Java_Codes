package HashMaps;

import java.util.HashSet;

public class basicSets {
    public static void main(String[] args) {
        HashSet<Integer> set = new HashSet<>();
        set.add(10);
        set.add(20);
        set.add(30);
        set.add(40);
        set.add(-5);
        System.out.println(set); 
        System.out.println(set.size()); 
        set.add(25);
        System.out.println(set.size());
        set.remove(30);
        System.out.println(set.size());
        System.out.println(set.contains(-5));      
    }
}
