import java.util.*;
public class QuickSort {
    public static void print(int arr[]){
        for(int ele : arr){
            System.out.print(ele + " ");
        }
        System.out.println();
    }
    public static void swap(int arr[], int i, int j){
        int temp = arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
    } 
    public static int partition(int arr[], int lo, int hi){
        int mid = (lo+hi)/2;
        int pivot = arr[mid], pivotidx=mid;
        int smallercount=0;
        for(int i=lo;i<=hi;i++){
            if(i==mid) continue;
            if(arr[i]<=pivot) smallercount++;
        }
        int correctidx=lo+smallercount;
        swap(arr, pivotidx, correctidx);
        int i=lo, j=hi;
        while(i<correctidx && j>correctidx){
            if(arr[i]<= pivot) i++; 
            else if(arr[j]>pivot) j--;
            else if(arr[i]>pivot && arr[j]<=pivot){
                swap(arr, i, j);
            }
        }
        return correctidx;
    }
    public static void quicksort(int arr[], int lo, int hi){
        if(lo>=hi) return;
        int idx = partition(arr, lo, hi);
        quicksort(arr, lo, idx-1);
        quicksort(arr, idx+1, hi);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n,i;
        System.out.println("Enter the array Limit");
        n=sc.nextInt();
        int arr[] = new int[n];
        System.out.println("Enter the array elements:-");
        for(i=0;i<n;i++){
            arr[i] = sc.nextInt();
        }
        print(arr);
        quicksort(arr, 0, n-1);
        print(arr);
    }
}
