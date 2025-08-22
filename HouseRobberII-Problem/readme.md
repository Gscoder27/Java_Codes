<h2><a href="https://leetcode.com/problems/house-robber-ii/">House Robber II</a></h2>
<h3>Medium</h3>
<hr>
<p>You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed. The houses are arranged in a circle, which means the first house is adjacent to the last one. Adjacent houses have security systems connected — if you rob two adjacent houses, the system will trigger and alert the police.</p>

<p>Given an integer array <code>nums</code> representing the amount of money in each house, return <strong>the maximum amount of money you can rob without alerting the police</strong>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> nums = [2,3,2]  
<strong>Output:</strong> 3  
<strong>Explanation:</strong> You cannot rob house 1 (2) and house 3 (2) because they are adjacent (due to the circular arrangement). 
The optimal choice is robbing house 2 only: 3.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> nums = [1,2,3,1]  
<strong>Output:</strong> 4  
<strong>Explanation:</strong> Rob houses 1 and 3: 1 + 3 = 4.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>
<ul>
  <li><code>1 ≤ nums.length ≤ 100</code></li>
  <li><code>0 ≤ nums[i] ≤ 1000</code></li>
</ul>

<p>&nbsp;</p>
<strong>Follow-up:</strong> Can you solve this in <code>O(n)</code> time and <code>O(1)</code> extra space using dynamic programming, by treating the circular arrangement smartly?

---

### Solution Approach

Because the first and last houses are adjacent (due to the circular layout), you cannot include both in the same robbery.  
To handle this:

1. **Exclude the first house** → apply the classic *House Robber I* solution on the subarray `nums[1...n-1]`.  
2. **Exclude the last house** → apply the same solution on the subarray `nums[0...n-2]`.  
3. Take the **maximum** of these two results.  

This effectively breaks the cycle and reduces the problem to a linear version twice.

---

### ⏱️ Time & Space Complexity

- **Time Complexity:** `O(n)` — the DP solution runs twice (linear work each).  
- **Space Complexity:** `O(1)` — optimized iterative DP using only two variables.
