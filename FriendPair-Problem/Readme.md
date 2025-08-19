<h2><a href="https://www.geeksforgeeks.org/problems/friends-pairing-problem5425/1">Friends Pairing Problem</a></h2>
<h3>Medium</h3>
<hr>
<p>Given <code>n</code> friends, each one can remain single or can be paired up with some other friend. Each friend can be paired only once. Find the total number of ways in which friends can remain single or be paired up.</p>

<p>Since the answer can be very large, return the result <strong>modulo 10<sup>9</sup> + 7</strong>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> n = 3
<strong>Output:</strong> 4
<strong>Explanation:</strong> 
{1}, {2}, {3} → all single
{(1,2), 3}
{(1,3), 2}
{(2,3), 1}
Hence, total 4 ways.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> n = 2
<strong>Output:</strong> 2
<strong>Explanation:</strong> 
{1}, {2} → both single
{(1,2)} → paired
Hence, total 2 ways.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
  <li><code>1 &lt;= n &lt;= 10<sup>4</sup></code></li>
  <li>Answer should be returned modulo <code>10<sup>9</sup> + 7</code></li>
</ul>

<p>&nbsp;</p>
<strong>Follow-up:&nbsp;</strong>Can you solve this problem using <code>Dynamic Programming</code> to achieve <code>O(n)</code> time complexity?
