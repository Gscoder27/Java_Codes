<h2><a href="https://leetcode.com/problems/stone-game/">Stone Game</a></h2>
<h3>Medium</h3>
<hr>
<p>Alice and Bob are playing a game with an even number of piles of stones. Each pile has a positive integer number of stones represented by <code>piles[i]</code>. They take turns, with Alice starting first, and on each turn, a player must take an entire pile of stones from either the beginning or the end of the row. The game ends when there are no more piles, and the player with the most stones wins. Assume both players play optimally, and the total number of stones is odd so there can't be a tie.</p>

<p>&nbsp;</p>
<p><strong class="example">Example:</strong></p>
<pre>
<strong>Input:</strong> piles = [5, 3, 4, 5]  
<strong>Output:</strong> true  
<strong>Explanation:</strong> Alice can always win by choosing the right strategy.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>
<ul>
  <li><code>2 ≤ piles.length ≤ 500</code> (even)</li>
  <li><code>1 ≤ piles[i] ≤ 10⁵</code></li>
</ul>

---

###  Solution Approaches & Complexity

| Approach                          | Time Complexity    | Space Complexity   | Description |
|-----------------------------------|---------------------|--------------------|-------------|
| **Mathematical Strategy**         |  O(1)              | O(1)              | Since Alice can pick either all odd or all even indexed piles, she always wins on even-length piles. Instant result. |
| **Dynamic Programming (2D DP)**   |  O(n²)             | O(n²)             | Use DP on subarrays: `dp[i][j] = max(piles[i] − dp[i+1][j], piles[j] − dp[i][j-1])`. If `dp[0][n-1] > 0`, Alice wins. |

---
