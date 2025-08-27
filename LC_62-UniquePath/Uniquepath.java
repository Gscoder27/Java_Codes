import java.util.*;

public class Uniquepath {
	
    // Simple recursion will give TLE.
    // Use Recursion + Memoization
    public int uniquePaths(int m, int n) {
        int dp[][] = new int[m][n];
        for(int i=0;i<m;i++)
            for(int j=0;j<n;j++)
                dp[i][j]=-1;
        
        // return path(0, 0, m, n);
        return path(0, 0, m, n, dp);
    }

    private int path(int row, int col, int m, int n, int[][] dp){
        if(row>=m || col>=n) return 0;
        if(row==m-1 || col==n-1) return 1;
        if(dp[row][col]!=-1) return dp[row][col];
        int dwn=path(row,col+1,m,n,dp);
        int rt=path(row+1,col,m,n,dp);
        // int res = dwn+rt;
        // return res;
        return dp[row][col]=dwn+rt;
    }

    // Tabulation
    public int uniquePaths2(int m, int n) {
        int dp[][] = new int[m][n];
        for(int i=0;i<m;i++)
            dp[i][0]=1;
        for(int j=0;j<n;j++)
            dp[0][j]=1;

        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                dp[i][j]=dp[i-1][j]+dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Uniquepath obj = new Uniquepath();

        int m = sc.nextInt();
        int n = sc.nextInt();

        System.out.println("The number of unique paths is : " + obj.uniquePaths(m, n));
        System.out.println("The number of unique paths by Tabulation is : " + obj.uniquePaths2(m, n));
    }

}
