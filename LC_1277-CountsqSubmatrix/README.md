<h2><a href="https://leetcode.com/problems/count-square-submatrices-with-all-ones/">Count Square Submatrices with All Ones</a></h2>
<h3>Medium</h3>
<hr>
<p>Given an <code>m × n</code> binary matrix filled with <code>0</code>s and <code>1</code>s, return the total number of square submatrices (of any size) whose elements are all <code>1</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<pre>
<strong>Input:</strong>
matrix = [
  [0,1,1,1],
  [1,1,1,1],
  [0,1,1,1]
]
<strong>Output:</strong> 15  
<strong>Explanation:</strong>
- There are 10 squares of size 1×1  
- There are 4 squares of size 2×2  
- There is 1 square of size 3×3  
Total = 10 + 4 + 1 = 15
</pre>

<p><strong class="example">Example 2:</strong></p>
<pre>
<strong>Input:</strong>
matrix = [
  [1,0,1],
  [1,1,0],
  [1,1,0]
]
<strong>Output:</strong> 7  
<strong>Explanation:</strong>
- 6 squares of size 1×1  
- 1 square of size 2×2  
Total = 7
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>
<ul>
  <li><code>1 ≤ m, n ≤ 300</code></li>
  <li><code>matrix[i][j]</code> is either <code>0</code> or <code>1</code></li>
</ul>

---

###  Solution Approaches & Complexity

| Approach                          | Time Complexity  | Space Complexity     | Summary |
|-----------------------------------|------------------|----------------------|---------|
| **Brute-Force (all sizes)**       | O(m·n·min(m, n)²)| O(1)                 | Check every possible square starting at each cell — inefficient. |
| **Bottom-Up DP (2D dp array)**    | O(m·n)           | O(m·n)               | `dp[i][j]` stores the max square size ending at `(i, j)` — standard and clear.|
| **In-Place DP (modify matrix)**   | O(m·n)           | O(1) additional      | Update the input matrix directly to count and accumulate — elegant and space-efficient.|
| **Space-Optimized (1D DP)**       | O(m·n)           | O(n)                 | Use a rolling array or temporary variable to only store one row — minimal extra memory.|

---