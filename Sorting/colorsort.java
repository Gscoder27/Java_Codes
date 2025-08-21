import java.util.*;
public class colorsort {
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int i,n;
        System.out.println("Enter the array size :-");
        n=sc.nextInt();

        int arr[] = new int[n];
        int countzero = 0 , countones = 0;

        for(i=0;i<n;i++){
            arr[i] = sc.nextInt();
        }

        for(i=0;i<n;i++){
            if(arr[i] == 0) countzero++;
            if(arr[i] == 1) countones++;
        }

        for(i=0;i<n;i++){
            if(i<countzero){
                arr[i] = 0;
            }
            else if(i<countones+countzero){
                arr[i] = 1;
            }
            else
            {
                arr[i] = 2;
            }
        }
        System.out.println("\n");

        for(int ele : arr){
            System.out.print(ele + " ");
        }
    }
}
