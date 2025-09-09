Part-B QnA :-

Expected Interview Questions

Q: Why did you choose ReentrantReadWriteLock?
A: It separates read and write access, maximizing concurrency while ensuring correctness.

Q: What if libraries were not allowed?
A: I would use synchronized, which guarantees safety but with less parallelism.

Q: How do you avoid deadlocks in fine-grained per-node locking?
A: Acquire locks in a consistent parent → child order.

Q: What’s the impact of storing sets of locked descendants?
A: Higher memory usage (O(n²)). Can be optimized by storing a counter per node.

Q: Can your solution scale if 1M threads access the tree?
A: With RWLock, read-heavy workloads scale better. With synchronized, performance degrades due to serialization.

In Part A, I built a locking system in Java for an M-ary tree with lock, unlock, and upgrade operations. In Part B, I had to make it thread-safe. I applied two strategies: first using Java’s built-in ReentrantReadWriteLock, and second using synchronized in case libraries aren’t allowed. Both approaches protect critical sections — isLocked, lockedBy, and lockedDescendents — ensuring correctness in a multithreaded environment. The tradeoff is concurrency: coarse-grained locks serialize operations, while fine-grained per-node locks give more parallelism but require strict lock ordering to avoid deadlocks.

Expected Questions in Part B

Q: Why did you choose ReentrantReadWriteLock?
A: It allows multiple threads to read concurrently while ensuring exclusive writes. It’s more flexible than synchronized.

Q: What if you couldn’t use concurrency libraries?
A: I’d use synchronized at the method level or per-node level to achieve mutual exclusion.

Q: How do you avoid deadlocks with fine-grained locking?
A: By always acquiring locks in a consistent order (parent → child).

Q: What optimization would you apply for upgrade?
A: Replace descendant sets with a counter (lockedDescCount) → makes upgrade O(h) instead of O(k + h).

Q: What’s the tradeoff between coarse and fine-grained locking?
A:

Coarse (whole tree lock): simpler, safe, but less concurrency.

Fine (per-node): more concurrency, but harder to implement, risk of deadlocks.

Expected Interview Questions

Q: What happens if two threads try to lock the same node?
A: Without synchronization, both may succeed → race condition. With locking, only one succeeds.

Q: Why not just lock the whole tree with synchronized?
A: Correct but reduces parallelism. Fine-grained locking (per-node) allows more concurrency.

Q: How do you prevent deadlocks with per-node locks?
A: Always acquire locks in parent-to-child order.

Q: What if libraries are banned?
A: Use synchronized or CAS with AtomicInteger.

Q: Can upgrade be optimized?
A: Yes, by using counters instead of storing descendant sets → reduces complexity.

🔹 Critical Sections in Our Code

=> Access/modification of:

    isLocked (flag)

    lockedBy (user ID)

    lockedDescendents set (ancestor updates)

=> These must be guarded either by:

    writeLock, or

    synchronized, or

    CAS loops.

Interview questions you should prepare for (and model answers)

Q: Why did you add a ReadWriteLock and not synchronized?
A: ReadWriteLock explicitly separates read and write modes: multiple readers can run concurrently. synchronized is coarser; ReadWriteLock is more expressive and better for future read-heavy extensions.

Q: Does your code still maintain the same algorithmic complexity?
A: Yes — the asymptotic time complexity of lock/unlock/upgrade is unchanged. Thread-safety adds synchronization overhead and reduces parallelism if we use a global lock, but ensures correctness.

Q: Could we have used per-node locking to increase concurrency?
A: Yes. Per-node locking can allow parallel operations on disjoint subtrees, but we must acquire locks in a global order (e.g., node id order) to avoid deadlocks. It’s more complex to implement correctly, especially for upgrade that may touch many nodes.

Q: How do you avoid deadlocks in the fine-grained approach?
A: By enforcing a global lock acquisition order (for example, always acquire locks in increasing nodeId), and releasing them in reverse. No cycles can form in lock acquisition ordering, so deadlocks cannot occur.

Q: What if an upgrade touches thousands of nodes — will acquiring many locks be slow?
A: It can be. In such heavy operations the fine-grained approach may degenerate to coarse-grained behavior. We can batch updates and do lock stripping, or use a hybrid approach (coarse lock for heavy ops, fine-grained otherwise).

Q: Could you use optimistic concurrency?
A: Yes — read data without locks, then use CAS or version checks and retry if data changed. This works well when conflicts are rare but increases code complexity and retry logic.

Q: How would you scale this design for a distributed system (real-world Namma Yatri)?
A: Use a distributed lock manager, consistent hashing to partition trees, or store locks in a central service (e.g., a transactional DB) with TTLs and leader election. Use sharding, replication, and async reconciliation to ensure availability and performance.    

Interviewer questions you may get

Q: Why didn’t you use ReentrantLock or ReadWriteLock?
A: I wanted to show how concurrency can be handled using Java’s built-in primitives (synchronized and wait/notify). This works even in restricted environments where external concurrency utilities aren’t allowed.

Q: How do you handle fairness (avoiding starvation)?
A: synchronized + notifyAll() ensures that all waiting threads are woken up, and JVM scheduling decides who enters next. For stricter fairness, I would implement a queue of waiting threads manually.

Q: Is synchronized less performant than ReentrantLock?
A: Yes, synchronized is simpler but has higher context switch overhead. ReentrantLock allows finer control (fairness, tryLock, etc.), but synchronized is good enough in many cases.

Q: Could you achieve per-node concurrency without libraries?
A: Yes — each TreeNode can serve as its own monitor (synchronized (node) { ... }). But I’d need to lock multiple nodes (ancestors + node), which requires a consistent global lock order to avoid deadlocks.

=> Key points:

    synchronized ensures mutual exclusion — only one thread can be inside lock, unlock, or upgradeLock at any time.

    notifyAll() wakes up other waiting threads after unlock.

    You could also use object-level synchronization (synchronized (this)) if you want finer control.

3. Complexity analysis without libraries

    Time Complexity:
    Still the same:

        lock → O(h)

        unlock → O(h)

        upgrade → O(k + h) (after optimization)

    Space Complexity:
    Still O(n), no change.

Thread safe code :-

    class MAryTree {
    private final TreeNode root;
    private final Map<String, TreeNode> nameToTreeNodeMapping;

    MAryTree(TreeNode root) {
        this.root = root;
        this.nameToTreeNodeMapping = new HashMap<>();
    }

    public synchronized boolean lock(String name, int id) {
        TreeNode r = nameToTreeNodeMapping.get(name);
        if (r == null) return false;
        if (r.isLocked || !r.lockedDescendents.isEmpty()) return false;

        TreeNode par = r.parent;
        while (par != null) {
            if (par.isLocked) return false;
            par = par.parent;
        }
        updateParentsAdd(r.parent, r);
        r.isLocked = true;
        r.lockedBy = id;
        return true;
    }

    public synchronized boolean unlock(String name, int id) {
        TreeNode r = nameToTreeNodeMapping.get(name);
        if (r == null) return false;
        if (!r.isLocked || r.lockedBy != id) return false;

        TreeNode par = r.parent;
        while (par != null) {
            par.lockedDescendents.remove(r);
            par = par.parent;
        }
        r.isLocked = false;
        r.lockedBy = -1;
        notifyAll();  // wake waiting threads
        return true;
    }

    public synchronized boolean upgradeLock(String name, int id) {
        TreeNode r = nameToTreeNodeMapping.get(name);
        if (r == null) return false;
        if (r.isLocked || r.lockedDescendents.isEmpty()) return false;

        for (TreeNode ld : r.lockedDescendents) {
            if (ld.lockedBy != id) return false;
        }

        TreeNode par = r.parent;
        while (par != null) {
            if (par.isLocked) return false;
            par = par.parent;
        }

        // Unlock descendants
        Set<TreeNode> copy = new HashSet<>(r.lockedDescendents);
        for (TreeNode ld : copy) {
            TreeNode p = ld.parent;
            while (p != null) {
                p.lockedDescendents.remove(ld);
                p = p.parent;
            }
            ld.isLocked = false;
            ld.lockedBy = -1;
        }

        updateParentsAdd(r.parent, r);
        r.isLocked = true;
        r.lockedBy = id;
        return true;
    }

    private void updateParentsAdd(TreeNode nodeAncestor, TreeNode curr) {
            TreeNode r = nodeAncestor;
            while (r != null) {
                r.lockedDescendents.add(curr);
                r = r.parent;
            }
        }
    }

✅ 2. Batch-Optimized Upgrade + Read/Write Lock (Pure Java)

Instead of locking each node separately, we use a global ReadWriteLock.

Reads (check if node is locked, is ancestor locked, etc.) can happen in parallel.

Writes (lock/unlock/upgrade) are exclusive.

import java.util.*;
import java.util.concurrent.locks.*;

class RWLockingTree {
    static class Node {
        int id;
        Node parent;
        List<Node> children = new ArrayList<>();
        boolean isLocked = false;
        int lockedBy = -1;

        Node(int id) {
            this.id = id;
        }
    }

    private final Node root;
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    RWLockingTree(int n) {
        root = new Node(0);
        for (int i = 1; i < n; i++) {
            root.children.add(new Node(i));
        }
    }

    boolean lock(Node node, int userId) {
        rwLock.writeLock().lock();
        try {
            if (node.isLocked) return false;
            node.isLocked = true;
            node.lockedBy = userId;
            return true;
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    boolean unlock(Node node, int userId) {
        rwLock.writeLock().lock();
        try {
            if (!node.isLocked || node.lockedBy != userId) return false;
            node.isLocked = false;
            node.lockedBy = -1;
            return true;
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    boolean upgrade(Node node, int userId) {
        rwLock.writeLock().lock();
        try {
            if (node.isLocked) return false;

            // check ancestors
            Node cur = node.parent;
            while (cur != null) {
                if (cur.isLocked) return false;
                cur = cur.parent;
            }

            // collect locked descendants
            List<Node> lockedDesc = new ArrayList<>();
            collectLockedDescendants(node, userId, lockedDesc);

            if (lockedDesc.isEmpty()) return false;

            for (Node d : lockedDesc) {
                d.isLocked = false;
                d.lockedBy = -1;
            }

            node.isLocked = true;
            node.lockedBy = userId;
            return true;
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    private void collectLockedDescendants(Node node, int userId, List<Node> result) {
        for (Node child : node.children) {
            if (child.isLocked) {
                if (child.lockedBy != userId) {
                    result.clear();
                    return;
                }
                result.add(child);
            }
            collectLockedDescendants(child, userId, result);
        }
    }
}

🔍 Analysis

Time Complexity: Same as above (O(n) worst-case upgrade).

Space Complexity: O(h).

Thread Safety: Achieved with ReadWriteLock.

Multiple threads can safely read (check lock status).

Only one can modify (lock/unlock/upgrade).

💡 Key Interview Questions You’ll Be Asked

Why per-node lock vs ReadWriteLock?

Per-node allows fine-grained parallelism (different subtrees can lock in parallel).

RWLock is simpler but may reduce concurrency.

Deadlocks:

How do you prevent them?

Answer: by enforcing strict parent-to-child ordering when acquiring locks.

Optimizations:

Maintain counters of locked descendants to make upgrade O(h) instead of O(n).

Failure Cases:

What happens if two users try to upgrade the same node?

What if a parent is locked while children are being unlocked?
