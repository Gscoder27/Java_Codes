import java.util.*;

public class Perfectsq {

        public boolean isPerfect(int n){
        int sqrt= (int)Math.sqrt(n);
        return sqrt*sqrt==n;
    }

    // Basic recursion (TLE):-
    // T.C.: O(n^n), S.C.: O(n)
    // public int numSquares(int n) {
    //     if(isPerfect(n)) return 1;
    //     int min = Integer.MAX_VALUE;
    //     for(int i=1;i<=n/2;i++){
    //         int cnt = numSquares(i) + numSquares(n-i);
    //         min=Math.min(cnt,min);
    //     }
    //     return min;
    // }

    // Recursion + Memoization (Still TLE but get's better) 
    // T.C.: O(n*n), S.C.: O(n)    
    // public int minSquares(int n, int dp[]){
    //     if(isPerfect(n)) return 1;
    //     if(dp[n]!=-1) return dp[n];
    //     int min = Integer.MAX_VALUE;
    //     for(int i=1;i<=n/2;i++){
    //         int cnt = minSquares(i, dp) + minSquares(n-i, dp);
    //         min=Math.min(cnt,min);
    //     }
    //     return dp[n]=min;
    // }
    // public int numSquares(int n){
    //     int dp[] = new int[n+1];
    //     Arrays.fill(dp,-1);
    //     return minSquares(n, dp);
    // }

    // Optimization 

    // public int numSquares(int n){
    //     int dp[] = new int[n+1];
    //     for(int i=1;i<=n;i++){
    //         if(isPerfect(i)) dp[i]=1;
    //         else{
    //             int min = Integer.MAX_VALUE;
    //             // we can use this for loop too:-
    //             // for(int j=1;j<=i/2;j++)
    //             for(int j=1;j*j<=i;j++){
    //                 // int cnt=dp[j]+dp[i-j];
    //                 int cnt = dp[j*j] + dp[i-j*j];
    //                 min=Math.min(cnt,min);
    //             }
    //             dp[i]=min;
    //         }
    //     }
    //     return dp[n];
    // }

    // Optimization-2 :-

    // public int minSquares(int n, int dp[]){
    //     if(isPerfect(n)) return 1;
    //     if(dp[n]!=-1) return dp[n];
    //     int min = Integer.MAX_VALUE;
    //     for(int i=1;i*i<=n;i++){
    //         int cnt = minSquares(i*i, dp) + minSquares(n-i*i, dp);
    //         min=Math.min(cnt,min);
    //     }
    //     return dp[n]=min;
    // }
    // public int numSquares(int n){
    //     int dp[] = new int[n+1];
    //     Arrays.fill(dp,-1);
    //     return minSquares(n, dp);
    // }

    // V.optimization :-
    // T.C.: O(n^3/2), S.C.: O(n)
    public int numSquares(int n) {
        int[] dp=new int[n+1];
        dp[0]=0;
        for (int i=1;i<=n;i++){
            dp[i]=n;
            for(int j=1;j*j<=i;j++){
                dp[i]=Math.min(dp[i],dp[i-j*j]+1);
            }
        }
        return dp[n];
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Perfectsq ps = new Perfectsq();
        System.out.println(ps.numSquares(n));
        sc.close();
    }
}
