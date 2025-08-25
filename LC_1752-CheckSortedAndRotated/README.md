<h2><a href="https://leetcode.com/problems/check-if-array-is-sorted-and-rotated/">Check if Array Is Sorted and Rotated</a></h2>
<h3>Easy</h3>
<hr>
<p>Given an array <code>nums</code>, return <strong>true</strong> if the array was originally sorted in non-decreasing order, then rotated some number of positions (including zero). Otherwise, return <strong>false</strong>.</p>

<p>There may be duplicates in the original array.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<pre>
<strong>Input:</strong> nums = [3,4,5,1,2]  
<strong>Output:</strong> true  
<strong>Explanation:</strong> The original sorted array is [1,2,3,4,5]. Rotating it by 2 positions gives [3,4,5,1,2].
</pre>

<p><strong class="example">Example 2:</strong></p>
<pre>
<strong>Input:</strong> nums = [2,1,3,4]  
<strong>Output:</strong> false  
<strong>Explanation:</strong> No sorted array can be rotated to form nums.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>
<ul>
  <li><code>1 ≤ nums.length ≤ 100</code></li>
  <li><code>1 ≤ nums[i] ≤ 100</code></li>
</ul>

<p>&nbsp;</p>
<strong>Follow-up Insight:</strong> Instead of brute-forcing rotations, we can determine validity by counting "drops"—points where an element is greater than the next (considering the array circularly). A valid rotated sorted array can have at most one such drop.

---

###  Solution Approach

1. Iterate through the array, including a wrap-around from the last element back to the first using modulo arithmetic.
2. Count how many times `nums[i] > nums[(i + 1) % n]`. Each of these is a "drop" in the otherwise non-decreasing sequence.
3. If the total number of drops is **0 or 1**, return `true`. Otherwise, it's not a rotated sorted array.

---

### ​ Time & Space Complexity

- **Time Complexity:** `O(n)` — single scan over the array (including the circular check) :contentReference[oaicite:0]{index=0}.  
- **Space Complexity:** `O(1)` — only a few constant variables are used during the process :contentReference[oaicite:1]{index=1}.

---