<h2><a href="https://leetcode.com/problems/perfect-squares/">Perfect Squares</a></h2>
<h3>Medium</h3>
<hr>
<p>Given a positive integer <code>n</code>, return the least number of perfect square numbers (e.g., 1, 4, 9, 16, ...) that sum to <code>n</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<pre>
<strong>Input:</strong> n = 12  
<strong>Output:</strong> 3  
<strong>Explanation:</strong> 12 = 4 + 4 + 4
</pre>

<p><strong class="example">Example 2:</strong></p>
<pre>
<strong>Input:</strong> n = 13  
<strong>Output:</strong> 2  
<strong>Explanation:</strong> 13 = 4 + 9
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>
<ul>
  <li><code>1 ≤ n ≤ 10⁴</code></li>
</ul>

---

###  Solution Approaches & Complexity

| Approach                                | Time Complexity   | Space Complexity | Notes                                                 |
|-----------------------------------------|--------------------|------------------|-------------------------------------------------------|
| **Dynamic Programming (1D array)**       | **O(n √n)**        | **O(n)**         | Bottom-up with dp[i] = min(dp[i-k²] + 1) for k² ≤ i |
| **BFS on State Graph**                   | **O(n √n)**        | **O(n)**         | Each number is a node; subtract squares as edges     |
| **Math + Theorem (Four Square Theorem)** | **O(√n)**          | **O(1)**         | Check if 1, 2, or 3 squares suffice; else answer = 4 |

---

###  Notes on Complexity Estimation

- **Dynamic Programming**  
  Iterates through all numbers up to n, and for each, checks possible square subtractions up to √n → **Time: O(n√n)**, **Space: O(n)**.

- **Breadth-First Search (BFS)**  
  Treats numbers from n down to 0 as graph nodes and perfect square subtractions as transitions — explores levels until reaching 0 → **Time: O(n√n)**, **Space: O(n)**.

- **Mathematical Approach (Lagrange's Four-Square Theorem)**  
  - If n is a perfect square → return 1  
  - If n = a² + b² → return 2  
  - If `n = 4^k * m` and `m mod 8 == 7` → return 4  
  - Otherwise → return 3  
  → **Time Complexity: O(√n)** (checking up to √n), **Space Complexity: O(1)**.

---
