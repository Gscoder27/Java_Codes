import java.util.*;

public class StringCompresion {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringCompresion obj = new StringCompresion();
        int n = sc.nextInt();
        char[] arr = new char[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.next().charAt(0);
        }
        int ans = obj.compress(arr);
        System.out.println(ans);
    }

    public int compress(char[] arr) {
    StringBuilder ans = new StringBuilder();
    int i = 0, j = 0;

    while (j < arr.length) {
        if (arr[j] == arr[i]) {
            j++;
        } else {
            ans.append(arr[i]);
            int len = j - i;
            if (len > 1) ans.append(len);
            i = j;
        }
    }

    // Handle the last group (very important!)
    ans.append(arr[i]);
    int len = j - i;
    if (len > 1) ans.append(len);

    // Copy back to arr
    for (i = 0; i < ans.length(); i++) {
        arr[i] = ans.charAt(i);
    }

        return ans.length();
    }

}
