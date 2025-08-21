package HashMaps;

import java.util.*;

public class Duplicatchar {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        String s;
        System.out.println("Enter a string:");
        s = sc.nextLine();
        int n=s.length();

        HashMap<Character, Integer> freq = new HashMap<>();

        for(int i=0; i<n; i++){
            char c = s.charAt(i);
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        System.out.println("Duplicate characters are:");
        for(Map.Entry<Character, Integer> entry : freq.entrySet()){
            if(entry.getValue() > 1){
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }
}
