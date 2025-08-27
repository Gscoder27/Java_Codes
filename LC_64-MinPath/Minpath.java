package MinPath;

import java.util.*;

public class Minpath{
    
    // Recursion + Memoization
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][] dp = new int[m][n];
        for (int[] row : dp) {
            Arrays.fill(row, -1); // initialize memo table
        }

        return helper(m - 1, n - 1, grid, dp);
    }

    private int helper(int i, int j, int[][] grid, int[][] dp) {
        // Base case: starting cell
        if (i == 0 && j == 0) return grid[0][0];

        // Out of bounds → return large number
        if (i < 0 || j < 0) return Integer.MAX_VALUE;

        // Already computed
        if (dp[i][j] != -1) return dp[i][j];

        // Recursive relation
        int fromTop = helper(i - 1, j, grid, dp);
        int fromLeft = helper(i, j - 1, grid, dp);

        return dp[i][j] = grid[i][j] + Math.min(fromTop, fromLeft);
    }

    // Tabulation
    public int minPathSum2(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[] dp = new int[n]; // 1D array for storing min path sums

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    dp[j] = grid[i][j];  // starting cell
                } else if (i == 0) {
                    dp[j] = dp[j - 1] + grid[i][j];  // first row
                } else if (j == 0) {
                    dp[j] = dp[j] + grid[i][j];  // first column
                } else {
                    dp[j] = grid[i][j] + Math.min(dp[j - 1], dp[j]);
                }
            }
        }

        return dp[n - 1];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Minpath obj = new Minpath();
        int m = sc.nextInt();
        int n = sc.nextInt();

        int grid[][] = new int[m][n];

        System.out.println("Enter the elements of the grid :- ");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        System.out.println("The minimum path sum is : " + obj.minPathSum(grid));
        System.out.println("The minimum path sum using tabulation is : " + obj.minPathSum2(grid));
    }
}

