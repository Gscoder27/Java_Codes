// package LC_1277-CountsqSubmatrix;

import java.util.*;
public class Count1s {
    public int countSquares(int[][] arr) {
        int m=arr.length;
        int n=arr[0].length;
        int cnt=0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(arr[i][j]==0) continue;
                if(i>0 && j>0){
                    arr[i][j] += Min(arr[i-1][j], arr[i-1][j-1], arr[i][j-1]);
                }
                cnt += arr[i][j];
            }
        }
        return cnt;      
    }

    private int Min(int a, int b, int c){
        int mini=Math.min(a, Math.min(b, c));
        return mini;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Count1s obj = new Count1s();
        int m=sc.nextInt();
        int n=sc.nextInt();

        int arr[][] = new int[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                arr[i][j]=sc.nextInt();
            }
        }

        System.out.println("The number of sq. matrices are : " + obj.countSquares(arr));
    }
}
