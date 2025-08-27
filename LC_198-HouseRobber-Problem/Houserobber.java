import java.util.*;

public class Houserobber {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int arr[] = new int[n];

        System.out.println("Enter the numbers :- ");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        // Recursion + Memoization approach :-

        int dp[] = new int[n];
        Arrays.fill(dp, -1);
        // System.out.println(rob(arr, n - 1, dp));
        System.out.println("By Recursion + Memoization: " + rob(arr, 0, dp));

        // Tabulation approach :-

        System.out.println("By Tabulation: " + robTab(arr));
    }

    private static int rob(int[] arr, int idx, int[] dp) {
        // if(idx < 0) return 0;
        if(idx>=arr.length) return 0;
        if(dp[idx]!=-1) return dp[idx];
        // int take = arr[idx] + rob(arr, idx-2, dp);
        int take = arr[idx] + rob(arr, idx+2, dp);
        // int skip = rob(arr, idx-1, dp);
        int skip = rob(arr, idx+1, dp);
        return dp[idx] = Math.max(take,skip);
    }

    private static int robTab(int[] arr) {
        int n = arr.length;
        if(n == 0) return 0;
        if(n == 1) return arr[0];

        int[] dp = new int[n];
        dp[0] = arr[0];
        if(n > 1) dp[1] = Math.max(arr[0], arr[1]);

        for(int i = 2; i < n; i++) {
            dp[i] = Math.max(arr[i] + dp[i-2], dp[i-1]);
        }
        return dp[n-1];
    }


}
