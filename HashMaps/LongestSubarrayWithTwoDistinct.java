package HashMaps;

import java.util.HashMap;

public class LongestSubarrayWithTwoDistinct {
    public static void main(String[] args) {
        int[] arr = {1, 0, 1, 4, 1, 4, 1, 2, 3};
        System.out.println("Length of the longest subarray with two distinct numbers: " + longestSubarrayWithTwoDistinct(arr));
    }

    public static int longestSubarrayWithTwoDistinct(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        HashMap<Integer, Integer> map = new HashMap<>();
        int maxLength = 0;
        int left = 0;

        for (int right = 0; right < arr.length; right++) {
            map.put(arr[right], map.getOrDefault(arr[right], 0) + 1);

            while (map.size() > 2) {
                map.put(arr[left], map.get(arr[left]) - 1);
                if (map.get(arr[left]) == 0) {
                    map.remove(arr[left]);
                }
                left++;
            }

            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }
}