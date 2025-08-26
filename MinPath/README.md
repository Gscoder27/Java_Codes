<h2><a href="https://leetcode.com/problems/minimum-path-sum/">Minimum Path Sum</a></h2>
<h3>Medium</h3>
<hr>
<p>Given an <code>m × n</code> grid filled with non-negative numbers, find a path from the top-left corner to the bottom-right corner that minimizes the sum of all numbers along the path. You can only move either <strong>down</strong> or <strong>right</strong> at any point.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<pre>
<strong>Input:</strong>
[
  [1,3,1],
  [1,5,1],
  [4,2,1]
]
<strong>Output:</strong> 7  
<strong>Explanation:</strong> The path 1 → 3 → 1 → 1 → 1 gives the minimum sum 7.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>
<ul>
  <li><code>m == grid.length</code></li>
  <li><code>n == grid[i].length</code></li>
  <li><code>1 ≤ m, n ≤ 200</code></li>
  <li><code>0 ≤ grid[i][j] ≤ 100</code></li>
</ul>

---

###  Solution Approaches & Complexity

| Approach                                     | Time Complexity | Space Complexity       | Description |
|----------------------------------------------|-----------------|------------------------|-------------|
| **Brute-force recursion (no memo)**          | O(2^(m+n))      | O(m+n) recursion stack | Pure recursion, exponentially many paths 

| **Recursion + Memoization (Top-Down DP)**    | O(m·n)          | O(m·n) (dp + stack)    | Cache subproblem results to avoid recomputation 

| **Tabulation (Bottom-Up DP)**                | O(m·n)          | O(m·n)                 | Iterative DP building full table row by row 

| **Space-Optimized DP (1D array)**            | O(m·n)          | O(n)                   | Only keep the previous row’s data, reduce memory 

| **In-place DP (modify grid directly)**       | O(m·n)          | O(1)                   | Overwrite input grid to save space, ideal for 


---
