<h2><a href="https://leetcode.com/problems/gcd-of-odd-and-even-sums/">GCD of Odd and Even Sums</a></h2>
<h3>Easy</h3>
<hr>
<p>Given a positive integer <code>n</code>, let:</p>
<ul>
  <li><strong>sumOdd</strong> be the sum of the first <code>n</code> odd numbers (i.e. 1 + 3 + 5 + ...)</li>
  <li><strong>sumEven</strong> be the sum of the first <code>n</code> even numbers (i.e. 2 + 4 + 6 + ...)</li>
</ul>
<p>Return the <strong>greatest common divisor (GCD)</strong> of <code>sumOdd</code> and <code>sumEven</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<pre>
<strong>Input:</strong> n = 5  
<strong>Output:</strong> 5  
<strong>Explanation:</strong> sumOdd = 1+3+5+7+9 = 25, sumEven = 2+4+6+8+10 = 30. GCD(25, 30) = 5.
</pre>

<p><strong class="example">Example 2:</strong></p>
<pre>
<strong>Input:</strong> n = 1  
<strong>Output:</strong> 1  
<strong>Explanation:</strong> sumOdd = 1, sumEven = 2. GCD(1, 2) = 1.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>
<ul>
  <li><code>1 ≤ n ≤ 1000</code></li>
</ul>

<p>&nbsp;</p>
<strong>Insight:</strong> Sum of first <code>n</code> odd numbers = <code>n²</code>; sum of first <code>n</code> even numbers = <code>n(n+1)</code>.  
Therefore, GCD(n², n(n+1)) = n × GCD(n, n+1). Since GCD(n, n+1) = 1 (consecutive numbers), **the answer is simply <code>n</code>**.

---

###  Time & Space Complexity
- **Time Complexity:** `O(1)` — single arithmetic operation.  
- **Space Complexity:** `O(1)`

---

###  Solution in Java

```java
public class Solution {
    public int sumEvenGrand(int n) {
        return n;  // Mathematical simplification: GCD of sums is always n.
    }
}


---

### Why This Works
- The **core insight** is that:
  - `sumOdd` = 1 + 3 + … = `n²`
  - `sumEven` = 2 + 4 + … = `n * (n + 1)`
  - Consecutive integers `n` and `n + 1` are always coprime, so `GCD(n², n(n+1)) = n`.
- That allows for a **constant-time** solution without loops or large computations.

---