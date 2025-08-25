package GCD;

import java.util.*;

public class GCDofEvenOddNum {

    public int gcdOfOddEvenSums(int n) {
        int cnt=n; 
        int Evsum=0; 
        int Odsum=0; 
        while(cnt!=0){ 
            int num=1; 
            if(num%2==0){ 
                Evsum += 
                    num; 
            } 
            else Odsum += num; 
            cnt--; 
            num++;
        } 
        return GCD(Evsum, Odsum); 
    }

    public int GCD(int a, int b){ 
        if (b == 0) return a; 
        return GCD(b, a % b);
    }

    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        GCDofEvenOddNum obj = new GCDofEvenOddNum(); 
        int n=sc.nextInt();
        System.out.println("The GCD is " + obj.gcdOfOddEvenSums(n));
        // M-2:- Find the GCD of two numbers
        // System.out.println("The GCD of first odd and even numbers is " + n);
    }
}
