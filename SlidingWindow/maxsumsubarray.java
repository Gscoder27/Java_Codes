package SlidingWindow;

import java.util.*;
class maxsumsubarray {
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr= new int[n];
        System.out.println("Enter the elements of the array");
        for(int i=0;i<n;i++){
            arr[i]=sc.nextInt();
        }
        System.out.println("Enter the value of k");
        int k=sc.nextInt();
        int sum=0;
        int max=0;
        for(int i=0;i<k;i++){
            sum+=arr[i];
        }
        max=sum;
        for(int i=k;i<n;i++){
            sum+=arr[i]-arr[i-k];
            max=Math.max(max,sum);
        }
        System.out.println("The max sum is :- "+max);
    }
}
