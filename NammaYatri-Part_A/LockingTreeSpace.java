// package NammaYatri-Part_A;

import java.io.*;
import java.util.*;

public class LockingTreeSpace {
    static class TreeNode {
        String name;
        int lockedBy; // -1 means unlocked
        boolean isLocked;
        List<TreeNode> childs;
        TreeNode parent;
        Set<TreeNode> lockedDescendents;

        TreeNode(String nm, TreeNode par) {
            name = nm;
            parent = par;
            lockedBy = -1;
            isLocked = false;
            childs = new ArrayList<>();
            lockedDescendents = new HashSet<>();
        }

        // create all children from list of names, set parent to this
        void addChild(List<String> names) {
            for (String nm : names) {
                TreeNode child = new TreeNode(nm, this);
                childs.add(child);
            }
        }
    }

    static class MAryTree {
        TreeNode root;
        Map<String, TreeNode> nameToTreeNodeMapping;

        MAryTree(String rootName) {
            root = new TreeNode(rootName, null);
            nameToTreeNodeMapping = new HashMap<>();
        }

        // build m-ary tree from level-order names array
        void makeMArtTree(String[] arr, int m) {
            ArrayDeque<TreeNode> q = new ArrayDeque<>();
            int n = arr.length;
            q.add(root);
            int k = 1; // next index to take children names from
            while (!q.isEmpty() && k <= n) {
                TreeNode cur = q.poll();
                nameToTreeNodeMapping.put(cur.name, cur);
                // collect up to m children from arr starting at k
                List<String> childNames = new ArrayList<>();
                for (int i = k; i < Math.min(n, k + m); ++i) {
                    childNames.add(arr[i]);
                }
                // create child nodes and push them in queue
                cur.addChild(childNames);
                for (TreeNode ch : cur.childs) {
                    q.add(ch);
                }
                k += childNames.size();
            }
        }

        // inform all ancestors that `curr` became a locked descendant
        void updateParents(TreeNode nodeAncestor, TreeNode curr) {
            TreeNode r = nodeAncestor;
            while (r != null) {
                r.lockedDescendents.add(curr);
                r = r.parent;
            }
        }

        boolean lock(String name, int id) {
            TreeNode r = nameToTreeNodeMapping.get(name);
            if (r == null)
                return false;
            // if node already locked OR has any locked descendant -> fail
            if (r.isLocked || !r.lockedDescendents.isEmpty())
                return false;
            // check ancestor locked
            TreeNode par = r.parent;
            while (par != null) {
                if (par.isLocked)
                    return false;
                par = par.parent;
            }
            // update ancestors' lockedDescendents sets
            updateParents(r.parent, r);
            r.isLocked = true;
            r.lockedBy = id;
            return true;
        }

        boolean unlock(String name, int id) {
            TreeNode r = nameToTreeNodeMapping.get(name);
            if (r == null)
                return false;
            if (!r.isLocked || r.lockedBy != id)
                return false;
            // inform ancestors to erase this locked descendant
            TreeNode par = r.parent;
            while (par != null) {
                par.lockedDescendents.remove(r);
                par = par.parent;
            }
            r.isLocked = false;
            r.lockedBy = -1;
            return true;
        }

        boolean upgradeLock(String name, int id) {
            TreeNode r = nameToTreeNodeMapping.get(name);
            if (r == null)
                return false;
            // must be unlocked and have at least one locked descendant
            if (r.isLocked || r.lockedDescendents.isEmpty())
                return false;
            // all locked descendants must be locked by same id
            for (TreeNode ld : r.lockedDescendents) {
                if (ld.lockedBy != id)
                    return false;
            }
            // no ancestor should be locked
            TreeNode par = r.parent;
            while (par != null) {
                if (par.isLocked)
                    return false;
                par = par.parent;
            }
            // copy set to avoid concurrent modification while unlocking
            Set<TreeNode> copy = new HashSet<>(r.lockedDescendents);
            for (TreeNode ld : copy) {
                // unlock each locked descendant (we pass id because those locked nodes must be
                // locked by id)
                // the unlock function will clean ancestor sets as needed
                unlock(ld.name, id);
            }
            // now lock r
            lock(name, id);
            return true;
        }

        // Optimized upgradeLock:- 
        // This has T.C.: O(k+d) where k is number of locked descendents and d is depth of tree
        // instead of O(k*h) where h is height of tree in previous approach
        boolean upgradeLock2(String name, int id) {
            TreeNode r = nameToTreeNodeMapping.get(name);
            if (r == null)
                return false;
            if (r.isLocked || r.lockedDescendents.isEmpty())
                return false;

            // 1. Validate all descendants locked by same id
            for (TreeNode ld : r.lockedDescendents) {
                if (ld.lockedBy != id)
                    return false;
            }

            // 2. Check ancestors are free
            TreeNode par = r.parent;
            while (par != null) {
                if (par.isLocked)
                    return false;
                par = par.parent;
            }

            // 3. Batch cleanup: instead of unlocking one by one
            for (TreeNode ld : r.lockedDescendents) {
                ld.isLocked = false;
                ld.lockedBy = -1;
            }
            r.lockedDescendents.clear(); // reset in one go

            // Update ancestor sets for this node only once
            updateParents(r.parent, r);
            r.isLocked = true;
            r.lockedBy = id;

            return true;
        }

    }

    // Fast input reader
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream is) {
            in = is;
        }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0)
                    return -1;
            }
            return buffer[ptr++];
        }

        String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = read()) <= ' ') {
                if (c == -1)
                    return null;
            }
            while (c > ' ') {
                sb.append((char) c);
                c = read();
            }
            return sb.toString();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int n = fs.nextInt();
        int m = fs.nextInt();
        int t = fs.nextInt();

        String[] arr = new String[n];
        for (int i = 0; i < n; ++i)
            arr[i] = fs.next();

        MAryTree tree = new MAryTree(arr[0]);
        tree.makeMArtTree(arr, m);

        StringBuilder out = new StringBuilder();
        for (int i = 0; i < t; ++i) {
            int opType = fs.nextInt();
            String name = fs.next();
            int id = fs.nextInt();
            boolean res;
            if (opType == 1)
                res = tree.lock(name, id);
            else if (opType == 2)
                res = tree.unlock(name, id);
            else
                res = tree.upgradeLock(name, id);
            out.append(res ? "true" : "false").append('\n');
        }
        System.out.print(out.toString());
    }
}
