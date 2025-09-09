// The overall T.C.: is O(h) for all ops lock, unlock, upgrade
// The overall S.C.: is O(n) for all ops lock, unlock, upgrade

import java.io.*;
import java.util.*;

public class MostOptimized {
    static class TreeNode {
        String name;
        int lockedBy;       // -1 means unlocked
        boolean isLocked;
        int lockedDescCount; // NEW: number of locked descendants
        List<TreeNode> childs;
        TreeNode parent;

        TreeNode(String nm, TreeNode par) {
            name = nm;
            parent = par;
            lockedBy = -1;
            isLocked = false;
            lockedDescCount = 0;
            childs = new ArrayList<>();
        }

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

        void makeMArtTree(String[] arr, int m) {
            ArrayDeque<TreeNode> q = new ArrayDeque<>();
            int n = arr.length;
            q.add(root);
            int k = 1;
            while (!q.isEmpty() && k < n) {
                TreeNode cur = q.poll();
                nameToTreeNodeMapping.put(cur.name, cur);
                List<String> childNames = new ArrayList<>();
                for (int i = k; i < Math.min(n, k + m); ++i) {
                    childNames.add(arr[i]);
                }
                cur.addChild(childNames);
                for (TreeNode ch : cur.childs) {
                    q.add(ch);
                }
                k += childNames.size();
            }
        }

        // Helper: update all ancestors' lockedDescCount
        private void updateParents(TreeNode node, int delta) {
            while (node != null) {
                node.lockedDescCount += delta;
                node = node.parent;
            }
        }

        boolean lock(String name, int id) {
            TreeNode r = nameToTreeNodeMapping.get(name);
            if (r == null) return false;

            // Cannot lock if already locked or has locked descendants
            if (r.isLocked || r.lockedDescCount > 0) return false;

            // Cannot lock if any ancestor is locked
            TreeNode par = r.parent;
            while (par != null) {
                if (par.isLocked) return false;
                par = par.parent;
            }

            // Safe to lock
            r.isLocked = true;
            r.lockedBy = id;
            updateParents(r.parent, 1);
            return true;
        }

        boolean unlock(String name, int id) {
            TreeNode r = nameToTreeNodeMapping.get(name);
            if (r == null || !r.isLocked || r.lockedBy != id) return false;

            // Unlock
            r.isLocked = false;
            r.lockedBy = -1;
            updateParents(r.parent, -1);
            return true;
        }

        boolean upgradeLock(String name, int id) {
            TreeNode r = nameToTreeNodeMapping.get(name);
            if (r == null) return false;

            // Must be unlocked and have locked descendants
            if (r.isLocked || r.lockedDescCount == 0) return false;

            // Check all descendants are locked by same id
            if (!allDescendantsSameUser(r, id)) return false;

            // Check ancestors not locked
            TreeNode par = r.parent;
            while (par != null) {
                if (par.isLocked) return false;
                par = par.parent;
            }

            // Unlock all descendants
            unlockAllDescendants(r, id);

            // Lock current node
            return lock(name, id);
        }

        // DFS helper: check if all locked descendants are locked by this user
        private boolean allDescendantsSameUser(TreeNode node, int id) {
            if (node.isLocked && node.lockedBy != id) return false;
            for (TreeNode child : node.childs) {
                if (!allDescendantsSameUser(child, id)) return false;
            }
            return true;
        }

        // DFS helper: unlock all descendants of a node
        private void unlockAllDescendants(TreeNode node, int id) {
            for (TreeNode child : node.childs) {
                unlockAllDescendants(child, id);
            }
            if (node.isLocked && node.lockedBy == id) {
                unlock(node.name, id);
            }
        }
    }

    // ---------- Fast input ----------
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastScanner(InputStream is) { in = is; }
        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }
        String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = read()) <= ' ') {
                if (c == -1) return null;
            }
            while (c > ' ') {
                sb.append((char) c);
                c = read();
            }
            return sb.toString();
        }
        int nextInt() throws IOException { return Integer.parseInt(next()); }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int n = fs.nextInt();
        int m = fs.nextInt();
        int t = fs.nextInt();

        String[] arr = new String[n];
        for (int i = 0; i < n; ++i) arr[i] = fs.next();

        MAryTree tree = new MAryTree(arr[0]);
        tree.makeMArtTree(arr, m);

        StringBuilder out = new StringBuilder();
        for (int i = 0; i < t; ++i) {
            int opType = fs.nextInt();
            String name = fs.next();
            int id = fs.nextInt();
            boolean res;
            if (opType == 1) res = tree.lock(name, id);
            else if (opType == 2) res = tree.unlock(name, id);
            else res = tree.upgradeLock(name, id);
            out.append(res ? "true" : "false").append('\n');
        }
        System.out.print(out.toString());
    }
}
