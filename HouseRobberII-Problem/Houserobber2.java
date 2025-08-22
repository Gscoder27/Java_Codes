import java.util.*;
public class Houserobber2 {

    // Recursion + Memoization

    private static int support(int[] arr, int idx, int[] dp) {
        // if(idx < 0) return 0;
        if(idx>=arr.length) return 0;
        if(dp[idx]!=-1) return dp[idx];
        // int take = arr[idx] + rob(arr, idx-2, dp);
        int take = arr[idx] + support(arr, idx+2, dp);
        // int skip = rob(arr, idx-1, dp);
        int skip = support(arr, idx+1, dp);
        return dp[idx] = Math.max(take,skip);
    }    

    public int rob2(int nums[]){
        int n=nums.length;
        List<Integer> a1 = new ArrayList<>(); 
        List<Integer> a2 = new ArrayList<>(); 
        if(n == 1) return nums[0];
        for(int i=0;i<n;i++){
            if(i!=0) a1.add(nums[i]);
            if(i!=n-1) a2.add(nums[i]);
        }
        Integer ar1[] = new Integer[a1.size()];
        ar1 = a1.toArray(ar1);

        Integer ar2[] = new Integer[a2.size()];
        ar2 = a2.toArray(ar2);

        // return Math.max(helper(ar1), helper(ar2));
        
        // Convert List<Integer> to int[] for support method

        int dp1[] = new int[ar1.length];
        int dp2[] = new int[ar2.length];
        Arrays.fill(dp1, -1);
        Arrays.fill(dp2, -1);
        int[] arrA1 = Arrays.stream(ar1).mapToInt(Integer::intValue).toArray();
        int[] arrA2 = Arrays.stream(ar2).mapToInt(Integer::intValue).toArray();
        return Math.max(support(arrA1, 0, dp1), support(arrA2, 0, dp2));
    }

    // Tabulation :-

    public int helper(Integer arr[]){
        int n = arr.length;
        int dp[] = new int[n];
        dp[0] = arr[0];
        if(n>1) dp[1] = Math.max(arr[0], arr[1]);
        for(int i=2; i<n; i++){
            dp[i] = Math.max(arr[i] + dp[i-2], dp[i-1]);
        }
        return dp[n-1];
    }

    public int rob(int[] nums) {
        int n=nums.length;
        List<Integer> a1 = new ArrayList<>(); 
        List<Integer> a2 = new ArrayList<>(); 
        if(n == 1) return nums[0];
        for(int i=0;i<n;i++){
            if(i!=0) a1.add(nums[i]);
            if(i!=n-1) a2.add(nums[i]);
        }
        Integer ar1[] = new Integer[a1.size()];
        ar1 = a1.toArray(ar1);

        Integer ar2[] = new Integer[a2.size()];
        ar2 = a2.toArray(ar2);

        return Math.max(helper(ar1), helper(ar2));
    }

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
        Houserobber2 sol = new Houserobber2();
        System.out.println("By Recursion + Memoization: " + sol.rob2(arr));

        // Tabulation approach :-

        System.out.println("By Tabulation: " + sol.rob(arr));
    }
}