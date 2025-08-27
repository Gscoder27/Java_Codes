<h2><a href="https://leetcode.com/problems/unique-paths/">Unique Paths</a></h2>
<h3>Medium</h3>
<hr>
<p>A robot is located at the top-left corner of an <code>m × n</code> grid (marked 'Start'). The robot can only move either <strong>down</strong> or <strong>right</strong> at any point in time. It is trying to reach the bottom-right corner of the grid (marked 'Finish'). Implement a function that returns the total number of unique paths the robot can take to reach the finish.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<pre>
<strong>Input:</strong> m = 3, n = 7  
<strong>Output:</strong> 28  
<strong>Explanation:</strong> There are 28 ways to move from the top-left to the bottom-right by moving only down or right.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>
<ul>
  <li><code>1 ≤ m, n ≤ 100</code></li>
  <li>Answer fits within a 32-bit integer.</li>
</ul>

---

###  Solution Approaches & Complexity Comparison

| Approach                                 | Time Complexity           | Space Complexity       | Notes |
|------------------------------------------|---------------------------|------------------------|-------|
| **Brute-force recursion**                | Exponential (~O(2^(m+n))) | O(m+n) recursion stack | Explores all paths naïvely; not feasible for large grids |
| **Recursion + Memoization (Top-Down DP)**| O(m·n)                    | O(m·n) + recursion     | Caches intermediate results to avoid redundant calculations |
| **Tabulation (Bottom-Up DP)**            | O(m·n)                    | O(m·n)                 | Standard iterative DP; builds a DP matrix row by row |
| **Space-Optimized 1D DP Array**          | O(m·n)                    | O(n)                   | Uses a single row (1D array), rolling over values per row |
| **Combinatorial (nCr) Formula**          | O(min(m,n)) or O(m+n)     | O(1)                   | Use combination formula: C((m-1)+(n-1), m-1) or equivalent calculation — fast and constant space. |

---
