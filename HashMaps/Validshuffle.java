package HashMaps;

import java.util.*;

public class Validshuffle{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String n1 = sc.nextLine();
        String n2 = sc.nextLine();
        String s = sc.nextLine();
        
        int n=n1.length();
        int len=n2.length();
        int ns=s.length();

        if(ns!=(n+len)){
            System.out.println("No");
            return;
        }

        HashMap<Character, Integer> freq = new HashMap<>();
        for(int i=0; i<n; i++){
            char c = n1.charAt(i);
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        for(int i=0; i<len; i++){
            char c = n2.charAt(i);
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        for(int i=0; i<ns; i++){
            if(freq.containsKey(s.charAt(i))){
                freq.put(s.charAt(i), freq.get(s.charAt(i)) - 1);
            } else {
                System.out.println("No");
                return;
            }
        }

        for(Map.Entry<Character, Integer> entry : freq.entrySet()){
            if(entry.getValue() != 0){
                System.out.println("No");
                return;
            }
        }
        System.out.println("Yes");
    }
}