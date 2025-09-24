// Wap to brign all -ve numbers to the left not necessary that the number is in a arranged order only all the -ve numbers should be on the left if the array

// There are multiple approaches either use inbuilt sort technique with T.C.: O(nlogn) or the methods like :-
// 1. Quick sort - T.C.:O(n), S.C.:O(1)
// 2. Two pointer - T.C.:O(n), S.C.:O(1)
// 3. Two pass - T.C.:O(n), S.C.:O(1)

import java.util.*;

public class NegativeLeft {

    // Two pointer approach :-
    // public void Helper(int arr[]) {
    //     int n = arr.length;
    //     int lft = 0, rt = n - 1;
    //     while (lft < rt) {
    //         while (lft < rt && arr[lft] < 0)
    //             lft++;
    //         while (rt > lft && arr[rt] > 0)
    //             rt--;

    //         if (lft < rt) {
    //             int temp = arr[lft];
    //             arr[lft] = arr[rt];
    //             arr[rt] = temp;
    //             lft++;
    //             rt--;
    //         }
    //     }
    // }

    // Two pass approach :-
    public void Helper2(int arr[]){
        int cnt=0;
        int n = arr.length;
        for(int i=0;i<n;i++){
            if(arr[i]<0) cnt++;
        }
        int i=0;
        for(int j=0;j<cnt;j++){
            while(i<n && arr[i]>0) i++;

            int tmp = arr[j];
            arr[j] = arr[i];
            arr[i] = tmp;
            i++;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        NegativeLeft obj = new NegativeLeft();
        int n = sc.nextInt();
        int arr[] = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        // obj.Helper(arr);
        obj.Helper2(arr);
        for (int i = 0; i < n; i++) {

            // System.out.print(arr[i]+", ");

            if (i < n - 1)
                System.out.print(arr[i] + ", ");
            else
                System.out.print(arr[i]); // last element without comma

            // M-2 : TO remove coma from last
            // System.out.println(String.join(", ",Arrays.stream(arr).mapToObj(String::valueOf).toArray(String[]::new)));
        }
        System.out.println();
    }
}