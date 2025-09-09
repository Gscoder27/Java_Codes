// This is an space optimized code O(n^2) worst case to O(nlog n) using counter approach for 'lockedDescendents' insted of Set

import java.io.*;
import java.util.*;

public class LockingofTreeSpace {
    static class TreeNode {
        String name;
        int lockedBy;             // -1 = unlocked
        boolean isLocked;
        int lockedDescCount;      // number of locked nodes in subtree
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
    }

    static class MAryTree {
        TreeNode root;
        Map<String, TreeNode> map;

        MAryTree(String rootName) {
            root = new TreeNode(rootName, null);
            map = new HashMap<>();
        }

        // Build M-ary tree in level order
        void makeMArtTree(String[] arr, int m) {
            ArrayDeque<TreeNode> q = new ArrayDeque<>();
            int n = arr.length;
            q.add(root);
            int k = 1;
            while (!q.isEmpty() && k < n) {
                TreeNode cur = q.poll();
                map.put(cur.name, cur);
                for (int i = 0; i < m && k < n; i++) {
                    TreeNode child = new TreeNode(arr[k++], cur);
                    cur.childs.add(child);
                    q.add(child);
                }
            }
        }

        boolean lock(String name, int id) {
            TreeNode node = map.get(name);
            if (node == null || node.isLocked || node.lockedDescCount > 0) return false;

            // check ancestor lock
            TreeNode par = node.parent;
            while (par != null) {
                if (par.isLocked) return false;
                par = par.parent;
            }

            // lock node
            node.isLocked = true;
            node.lockedBy = id;

            // update ancestors' lockedDescCount
            par = node.parent;
            while (par != null) {
                par.lockedDescCount++;
                par = par.parent;
            }
            return true;
        }

        boolean unlock(String name, int id) {
            TreeNode node = map.get(name);
            if (node == null || !node.isLocked || node.lockedBy != id) return false;

            // unlock node
            node.isLocked = false;
            node.lockedBy = -1;

            // update ancestors' lockedDescCount
            TreeNode par = node.parent;
            while (par != null) {
                par.lockedDescCount--;
                par = par.parent;
            }
            return true;
        }

        boolean upgradeLock(String name, int id) {
            TreeNode node = map.get(name);
            if (node == null || node.isLocked || node.lockedDescCount == 0) return false;

            // check ancestors not locked
            TreeNode par = node.parent;
            while (par != null) {
                if (par.isLocked) return false;
                par = par.parent;
            }

            // collect all locked descendants
            List<TreeNode> lockedNodes = new ArrayList<>();
            if (!collectLocked(node, id, lockedNodes)) return false;

            // unlock all locked descendants
            for (TreeNode ln : lockedNodes) {
                unlock(ln.name, id);
            }

            // lock current node
            return lock(name, id);
        }

        // DFS to check if all locked descendants are by same user
        private boolean collectLocked(TreeNode node, int id, List<TreeNode> lockedNodes) {
            if (node.isLocked) {
                if (node.lockedBy != id) return false;
                lockedNodes.add(node);
            }
            for (TreeNode ch : node.childs) {
                if (!collectLocked(ch, id, lockedNodes)) return false;
            }
            return true;
        }
    }

    // Fast Input
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
        for (int i = 0; i < n; i++) arr[i] = fs.next();

        MAryTree tree = new MAryTree(arr[0]);
        tree.makeMArtTree(arr, m);

        StringBuilder out = new StringBuilder();
        for (int i = 0; i < t; i++) {
            int op = fs.nextInt();
            String name = fs.next();
            int id = fs.nextInt();
            boolean res = (op == 1) ? tree.lock(name, id) : (op == 2) ? tree.unlock(name, id) : tree.upgradeLock(name, id);
            out.append(res ? "true" : "false").append("\n");
        }
        System.out.print(out.toString());
    }
}
