<h2><a href="https://leetcode.com/problems/string-compression/">String Compression</a></h2>
<h3>Medium</h3>
<hr>
<p>Given an array of characters <code>chars</code>, compress it using the following algorithm:</p>
<ul>
  <li>Start with an empty string <code>s</code>. For each group of consecutive repeating characters in <code>chars</code>:</li>
  <li>If the group's length is 1, append the character to <code>s</code>.</li>
  <li>Otherwise, append the character followed by the group's length.</li>
</ul>
<p>The compressed string <code>s</code> should <strong>not</strong> be returned separately, but instead be stored **in-place** in the input character array <code>chars</code>. Group lengths of 10 or more should be split into individual characters. After modifying the input array, return the new length of the compressed array. You must use **constant extra space**.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<pre>
<strong>Input:</strong> chars = ["a","a","b","b","c","c","c"]  
<strong>Output:</strong> 6  
<strong>Explanation:</strong> Compressing groups "aa", "bb", and "ccc" gives "a2b2c3". The first 6 chars of the array become ["a","2","b","2","c","3"].
</pre>

<p><strong class="example">Example 2:</strong></p>
<pre>
<strong>Input:</strong> chars = ["a"]  
<strong>Output:</strong> 1  
<strong>Explanation:</strong> Only one character, so it remains "a".
</pre>

<p><strong class="example">Example 3:</strong></p>
<pre>
<strong>Input:</strong> chars = ["a","b","b","b","b","b","b","b","b","b","b","b","b"]  
<strong>Output:</strong> 4  
<strong>Explanation:</strong> Groups are "a" and "bbbbbbbbbbbb", compressed to "ab12".
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>
<ul>
  <li><code>1 ≤ chars.length ≤ 2000</code></li>
  <li><code>chars[i]</code> is a lowercase or uppercase English letter, digit, or symbol.</li>
</ul>

---

###  Solution Approach

Use a **read-write pointer technique** to compress in-place:

1. Initialize two pointers: 
   - `read` — to scan through the input array,
   - `write` — to write back the compressed result.
2. For each group of identical characters (`chars[read]`):
   - Count how many times it repeats by advancing `read`.
   - Write the character at `chars[write++]`.
   - If count > 1, convert it to string and write each digit back into `chars`, advancing `write`.
3. Repeat until the end of the array, then return `write` — the length of the compressed string.

---

### ​ Time & Space Complexity

- **Time Complexity:** `O(n)` 
- **Space Complexity:** `O(n)`

---
