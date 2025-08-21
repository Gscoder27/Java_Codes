import java.util.*;
public class QuickSelect {
    static int ans;
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
    public static void quickselect(int arr[], int lo, int hi, int k){
        if(lo>hi) return;
        int idx = partition(arr, lo, hi);
        if(idx==k-1) {
            ans = arr[idx];
            return;
        }
        if(k-1<idx) quickselect(arr, lo, idx-1,k);
        else quickselect(arr, idx+1, hi,k);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n,i,k;
        ans=-1;
        System.out.println("Enter the array Limit");
        n=sc.nextInt();
        int arr[] = new int[n];
        System.out.println("Enter the array elements:-");
        for(i=0;i<n;i++){
            arr[i] = sc.nextInt();
        }
        System.out.println("Enter the Kth element to find");
        k=sc.nextInt();
        print(arr);
        quickselect(arr, 0, n-1,n-k+1);
        System.out.println(ans);
        // print(arr);
    }
}
