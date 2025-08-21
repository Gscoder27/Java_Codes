import java.util.*;
public class Arrays0s1s {
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int i,j,n;
        System.out.println("Enter the array size :-");
        n=sc.nextInt();

        int arr[] = new int[n];

        for(i=0;i<n;i++){
            arr[i] = sc.nextInt();
        }

        i=0;j=n-1;
        while(i<j){
            if(arr[i] == 0){
                i++;
            }
            if(arr[j] == 1){
                j--;
            }
            if(i<j && arr[i] == 1 && arr[j] == 0){
                arr[i] = 0;
                arr[j] = 1;
                i++;
                j--;
            }
        }

        for(int ele : arr){
            System.out.print(ele + " ");
        }
    }
}
