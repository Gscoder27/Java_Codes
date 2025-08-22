<h2><a href="https://leetcode.com/problems/house-robber/">House Robber</a></h2>
<h3>Medium</h3>
<hr>
<p>You are a professional robber planning to rob houses along a street. Every house has a certain amount of money stashed. The only constraint that stops you from robbing each of them is that adjacent houses have security systems connected — it will automatically contact the police if two adjacent houses were broken into on the same night.</p>

<p>Given an integer array <code>nums</code> representing the amount of money in each house, return <strong>the maximum amount of money you can rob tonight without alerting the police</strong>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> nums = [1,2,3,1]
<strong>Output:</strong> 4
<strong>Explanation:</strong> Rob houses 1 and 3 (1-based index): 1 + 3 = 4.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> nums = [2,7,9,3,1]
<strong>Output:</strong> 12
<strong>Explanation:</strong> Rob houses 1, 3, and 5: 2 + 9 + 1 = 12.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
  <li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
  <li><code>0 &lt;= nums[i] &lt;= 10<sup>4</sup></code></li>
</ul>

<p>&nbsp;</p>
<strong>Follow-up:</strong> Can you solve it in <code>O(n)</code> time and <code>O(1)</code> extra space using dynamic programming (iterative), without recursion or an extra array?

### ⏳ Time Complexity

- **Recursion + Memoization:** `O(n)` (since each index is computed once and stored in `dp`)  
- **Tabulation:** `O(n)` (single loop over the houses)  
- **Space Complexity:** `O(n)` (for the DP array in both approaches)

