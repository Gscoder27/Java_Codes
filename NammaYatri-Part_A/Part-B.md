Making code Thread safe :-

// -------- Variant A: synchronized whole-tree --------

    static class WholeTreeSync implements TreeManager {
        private final Node root;
        private final Map<String, Node> map = new HashMap<>();

        WholeTreeSync(String rootName) {
            root = new Node(0, rootName, null);
            map.put(root.name, root);
        }

        void build(String[] arr, int m) {
            ArrayDeque<Node> q = new ArrayDeque<>();
            q.add(root);
            int idx = 1, idCounter = 1;
            while (!q.isEmpty() && idx < arr.length) {
                Node cur = q.poll();
                for (int i = 0; i < m && idx < arr.length; ++i) {
                    Node ch = new Node(idCounter++, arr[idx++], cur);
                    cur.addChild(ch);
                    q.add(ch);
                    map.put(ch.name, ch);
                }
            }
        }

        // helper: update ancestor counters (assumes caller holds lock)
        private void updateParents(Node x, int delta) {
            Node cur = x;
            while (cur != null) {
                cur.lockedDescCount += delta;
                cur = cur.parent;
            }
        }

        // synchronized ensures only one thread is in these methods at a time
        @Override
        public synchronized boolean lock(String name, int uid) {
            Node r = map.get(name);
            if (r == null) return false;
            if (r.isLocked || r.lockedDescCount > 0) return false;
            Node p = r.parent;
            while (p != null) {
                if (p.isLocked) return false;
                p = p.parent;
            }
            r.isLocked = true;
            r.lockedBy = uid;
            updateParents(r.parent, 1);
            return true;
        }

        @Override
        public synchronized boolean unlock(String name, int uid) {
            Node r = map.get(name);
            if (r == null) return false;
            if (!r.isLocked || r.lockedBy != uid) return false;
            r.isLocked = false;
            r.lockedBy = -1;
            updateParents(r.parent, -1);
            return true;
        }

        @Override
        public synchronized boolean upgrade(String name, int uid) {
            Node r = map.get(name);
            if (r == null) return false;
            if (r.isLocked || r.lockedDescCount == 0) return false;

            // verify all locked descendants belong to uid (DFS)
            if (!allDescLockedBy(r, uid)) return false;

            // verify ancestors not locked
            Node p = r.parent;
            while (p != null) {
                if (p.isLocked) return false;
                p = p.parent;
            }

            // unlock all locked descendants directly
            unlockAllDescendantsDirect(r, uid);

            // lock current node
            r.isLocked = true;
            r.lockedBy = uid;
            updateParents(r.parent, 1);
            return true;
        }

        private boolean allDescLockedBy(Node node, int uid) {
            for (Node ch : node.children) {
                if (ch.isLocked && ch.lockedBy != uid) return false;
                if (!allDescLockedBy(ch, uid)) return false;
            }
            return true;
        }

        private void unlockAllDescendantsDirect(Node node, int uid) {
            for (Node ch : node.children) unlockAllDescendantsDirect(ch, uid);
            if (node.isLocked && node.lockedBy == uid) {
                node.isLocked = false;
                node.lockedBy = -1;
                updateParents(node.parent, -1);
            }
        }
    }

 // ------ Variant B: Per-node fine-grained synchronized (no libs) ------
    static class PerNodeSync implements TreeManager {
        private final Node root;
        private final Map<String, Node> map = new HashMap<>();

        PerNodeSync(String rootName) {
            root = new Node(0, rootName, null);
            map.put(root.name, root);
        }

        void build(String[] arr, int m) {
            ArrayDeque<Node> q = new ArrayDeque<>();
            q.add(root);
            int idx = 1, idCounter = 1;
            while (!q.isEmpty() && idx < arr.length) {
                Node cur = q.poll();
                for (int i = 0; i < m && idx < arr.length; ++i) {
                    Node ch = new Node(idCounter++, arr[idx++], cur);
                    cur.addChild(ch);
                    q.add(ch);
                    map.put(ch.name, ch);
                }
            }
        }

        // Acquire monitors for a list of nodes in increasing id order (deadlock-free).
        // This method holds all monitors for the duration of the callback, then releases.
        private <T> T withLocksOrdered(List<Node> nodes, java.util.function.Supplier<T> action) {
            // sort by id to maintain global order
            nodes.sort(Comparator.comparingInt(a -> a.id));
            return withLocksRecursive(nodes, 0, action);
        }

        // recursively nest synchronized blocks to hold multiple monitors
        private <T> T withLocksRecursive(List<Node> nodes, int idx, java.util.function.Supplier<T> action) {
            if (idx == nodes.size()) {
                return action.get();
            } else {
                Node cur = nodes.get(idx);
                synchronized (cur) {
                    return withLocksRecursive(nodes, idx + 1, action);
                }
            }
        }

        // Build list of ancestors (from root down to node)
        private List<Node> ancestorsList(Node node) {
            LinkedList<Node> list = new LinkedList<>();
            Node cur = node;
            while (cur != null) {
                list.add(cur);
                cur = cur.parent;
            }
            return list; // includes node and ancestors
        }

        // helper: update parents counters (assumes relevant monitors are held)
        private void updateParentsDirect(Node node, int delta) {
            Node cur = node;
            while (cur != null) {
                cur.lockedDescCount += delta;
                cur = cur.parent;
            }
        }

        @Override
        public boolean lock(String name, int uid) {
            Node r = map.get(name);
            if (r == null) return false;
            // Need to lock node + all ancestors to check conditions and update counters
            List<Node> lockTargets = new ArrayList<>(ancestorsList(r)); // r and ancestors
            // Use ordered locks to avoid deadlock
            return withLocksOrdered(lockTargets, () -> {
                if (r.isLocked || r.lockedDescCount > 0) return false;
                for (Node a : lockTargets) { // includes r
                    if (a != r && a.isLocked) return false;
                }
                // Safe to lock
                r.isLocked = true;
                r.lockedBy = uid;
                updateParentsDirect(r.parent, 1);
                return true;
            });
        }

        @Override
        public boolean unlock(String name, int uid) {
            Node r = map.get(name);
            if (r == null) return false;
            // lock node and ancestors to safely update counters
            List<Node> lockTargets = new ArrayList<>(ancestorsList(r));
            return withLocksOrdered(lockTargets, () -> {
                if (!r.isLocked || r.lockedBy != uid) return false;
                r.isLocked = false;
                r.lockedBy = -1;
                updateParentsDirect(r.parent, -1);
                return true;
            });
        }

        @Override
        public boolean upgrade(String name, int uid) {
            Node r = map.get(name);
            if (r == null) return false;
            // We need to examine: node r, ancestors, and (potentially) locked descendants.
            // Strategy:
            // 1) Acquire locks on ancestors + r (ordered) to verify ancestor/state constraints.
            // 2) If lockedDescCount==0 -> fail.
            // 3) Collect locked descendants (we will lock them individually when unlocking) - to avoid holding locks for huge subtree we will:
            //    - Collect references (no locking yet), then lock those nodes in ordered manner before modifying them.
            List<Node> anc = new ArrayList<>(ancestorsList(r));
            // Step 1: check ancestors and state
            boolean ok = withLocksOrdered(anc, () -> {
                if (r.isLocked || r.lockedDescCount == 0) return false;
                for (Node a : anc) if (a != r && a.isLocked) return false;
                return true;
            });
            if (!ok) return false;

            // Step 2: gather locked descendant nodes (no locks yet)
            List<Node> lockedDescList = new ArrayList<>();
            collectLockedNodes(r, lockedDescList);
            if (lockedDescList.isEmpty()) return false;

            // verify all locked descendants belong to uid
            for (Node d : lockedDescList) {
                synchronized (d) {
                    if (!d.isLocked || d.lockedBy != uid) return false;
                }
            }

            // Acquire locks on all nodes we will modify: ancestors+ r + lockedDescList (in global order)
            Set<Node> toLockSet = new HashSet<>();
            toLockSet.addAll(anc); // ancestors include r
            toLockSet.addAll(lockedDescList);
            List<Node> toLock = new ArrayList<>(toLockSet);

            // Now with all relevant locks held (ordered), perform unlock of descendants and lock r
            return withLocksOrdered(toLock, () -> {
                // double-check conditions (in case of races)
                if (r.isLocked) return false;
                for (Node a : anc) if (a != r && a.isLocked) return false;

                for (Node d : lockedDescList) {
                    if (d.isLocked && d.lockedBy == uid) {
                        d.isLocked = false;
                        d.lockedBy = -1;
                        updateParentsDirect(d.parent, -1);
                    } else {
                        return false; // inconsistent
                    }
                }
                // Finally lock r
                r.isLocked = true;
                r.lockedBy = uid;
                updateParentsDirect(r.parent, 1);
                return true;
            });
        }

        // collect locked nodes under subtree rooted at node
        private void collectLockedNodes(Node node, List<Node> acc) {
            for (Node ch : node.children) {
                if (ch.isLocked) acc.add(ch);
                collectLockedNodes(ch, acc);
            }
        }
    }

// --------- Variant C: ReentrantReadWriteLock based (uses java.util.concurrent.locks) ---------

    static class RWLockManager implements TreeManager {
        private final Node root;
        private final Map<String, Node> map = new HashMap<>();
        private final ReadWriteLock rw = new ReentrantReadWriteLock(true);

        RWLockManager(String rootName) {
            root = new Node(0, rootName, null);
            map.put(root.name, root);
        }

        void build(String[] arr, int m) {
            ArrayDeque<Node> q = new ArrayDeque<>();
            q.add(root);
            int idx = 1, idCounter = 1;
            while (!q.isEmpty() && idx < arr.length) {
                Node cur = q.poll();
                for (int i = 0; i < m && idx < arr.length; ++i) {
                    Node ch = new Node(idCounter++, arr[idx++], cur);
                    cur.addChild(ch);
                    q.add(ch);
                    map.put(ch.name, ch);
                }
            }
        }

        private void updateParents(Node x, int delta) {
            Node cur = x;
            while (cur != null) {
                cur.lockedDescCount += delta;
                cur = cur.parent;
            }
        }

        @Override
        public boolean lock(String name, int uid) {
            rw.writeLock().lock();
            try {
                Node r = map.get(name);
                if (r == null) return false;
                if (r.isLocked || r.lockedDescCount > 0) return false;
                Node p = r.parent;
                while (p != null) {
                    if (p.isLocked) return false;
                    p = p.parent;
                }
                r.isLocked = true;
                r.lockedBy = uid;
                updateParents(r.parent, 1);
                return true;
            } finally {
                rw.writeLock().unlock();
            }
        }

        @Override
        public boolean unlock(String name, int uid) {
            rw.writeLock().lock();
            try {
                Node r = map.get(name);
                if (r == null) return false;
                if (!r.isLocked || r.lockedBy != uid) return false;
                r.isLocked = false;
                r.lockedBy = -1;
                updateParents(r.parent, -1);
                return true;
            } finally {
                rw.writeLock().unlock();
            }
        }

        @Override
        public boolean upgrade(String name, int uid) {
            rw.writeLock().lock();
            try {
                Node r = map.get(name);
                if (r == null) return false;
                if (r.isLocked || r.lockedDescCount == 0) return false;
                if (!allDescLockedBy(r, uid)) return false;
                Node p = r.parent;
                while (p != null) {
                    if (p.isLocked) return false;
                    p = p.parent;
                }
                // unlock all locked descendants directly
                unlockAllDescendantsDirect(r, uid);
                // lock r
                r.isLocked = true;
                r.lockedBy = uid;
                updateParents(r.parent, 1);
                return true;
            } finally {
                rw.writeLock().unlock();
            }
        }

        private boolean allDescLockedBy(Node node, int uid) {
            for (Node ch : node.children) {
                if (ch.isLocked && ch.lockedBy != uid) return false;
                if (!allDescLockedBy(ch, uid)) return false;
            }
            return true;
        }

        private void unlockAllDescendantsDirect(Node node, int uid) {
            for (Node ch : node.children) unlockAllDescendantsDirect(ch, uid);
            if (node.isLocked && node.lockedBy == uid) {
                node.isLocked = false;
                node.lockedBy = -1;
                updateParents(node.parent, -1);
            }
        }
    }

########################### M - 2 :- #################################

static class MAryTree {
        final TreeNode root;
        final Map<String, TreeNode> map = new HashMap<>();

        MAryTree(String rootName) {
            this.root = new TreeNode(rootName, null);
            map.put(root.name, root);
        }

        void makeMArtTree(String[] arr, int m) {
            ArrayDeque<TreeNode> q = new ArrayDeque<>();
            q.add(root);
            int n = arr.length, idx = 1;
            while (!q.isEmpty() && idx < n) {
                TreeNode cur = q.poll();
                for (int i = 0; i < m && idx < n; ++i) {
                    TreeNode ch = new TreeNode(arr[idx++], cur);
                    cur.childs.add(ch);
                    q.add(ch);
                    map.put(ch.name, ch);
                }
            }
        }

        private void updateParents(TreeNode node, int delta) {
            while (node != null) {
                node.lockedDescCount += delta;
                node = node.parent;
            }
        }

        // synchronized methods ensure mutual exclusion on 'this' MAryTree instance
        public synchronized boolean lock(String name, int uid) {
            TreeNode r = map.get(name);
            if (r == null) return false;
            if (r.isLocked || r.lockedDescCount > 0) return false;
            TreeNode p = r.parent;
            while (p != null) {
                if (p.isLocked) return false;
                p = p.parent;
            }
            r.isLocked = true;
            r.lockedBy = uid;
            updateParents(r.parent, 1);
            return true;
        }

        public synchronized boolean unlock(String name, int uid) {
            TreeNode r = map.get(name);
            if (r == null) return false;
            if (!r.isLocked || r.lockedBy != uid) return false;
            r.isLocked = false;
            r.lockedBy = -1;
            updateParents(r.parent, -1);
            return true;
        }

        public synchronized boolean upgrade(String name, int uid) {
            TreeNode r = map.get(name);
            if (r == null) return false;
            if (r.isLocked || r.lockedDescCount == 0) return false;

            if (!allDescLockedBy(r, uid)) return false;

            TreeNode p = r.parent;
            while (p != null) {
                if (p.isLocked) return false;
                p = p.parent;
            }

            unlockAllDescendantsDirect(r, uid);

            r.isLocked = true;
            r.lockedBy = uid;
            updateParents(r.parent, 1);
            return true;
        }

        private boolean allDescLockedBy(TreeNode node, int uid) {
            for (TreeNode ch : node.childs) {
                if (ch.isLocked && ch.lockedBy != uid) return false;
                if (!allDescLockedBy(ch, uid)) return false;
            }
            return true;
        }

        private void unlockAllDescendantsDirect(TreeNode node, int uid) {
            for (TreeNode ch : node.childs) unlockAllDescendantsDirect(ch, uid);
            if (node.isLocked && node.lockedBy == uid) {
                node.isLocked = false;
                node.lockedBy = -1;
                updateParents(node.parent, -1);
            }
        }
    }

################################## M - 3 :- ####################################

static class MAryTree {
        final TreeNode root;
        final Map<String, TreeNode> map = new HashMap<>();
        private final ReadWriteLock rw = new ReentrantReadWriteLock(true);

        MAryTree(String rootName) {
            this.root = new TreeNode(rootName, null);
            map.put(root.name, root);
        }

        void makeMArtTree(String[] arr, int m) {
            ArrayDeque<TreeNode> q = new ArrayDeque<>();
            q.add(root);
            int n = arr.length;
            int idx = 1;
            while (!q.isEmpty() && idx < n) {
                TreeNode cur = q.poll();
                for (int i = 0; i < m && idx < n; ++i) {
                    TreeNode ch = new TreeNode(arr[idx++], cur);
                    cur.childs.add(ch);
                    q.add(ch);
                    map.put(ch.name, ch);
                }
            }
        }

        // helper: update lockedDescCount up the ancestor chain; assumes write lock held
        private void updateParents(TreeNode node, int delta) {
            while (node != null) {
                node.lockedDescCount += delta;
                node = node.parent;
            }
        }

        // lock operation (thread-safe)
        boolean lock(String name, int uid) {
            rw.writeLock().lock();
            try {
                TreeNode r = map.get(name);
                if (r == null) return false;
                if (r.isLocked || r.lockedDescCount > 0) return false;
                TreeNode p = r.parent;
                while (p != null) {
                    if (p.isLocked) return false;
                    p = p.parent;
                }
                r.isLocked = true;
                r.lockedBy = uid;
                updateParents(r.parent, 1);
                return true;
            } finally {
                rw.writeLock().unlock();
            }
        }

        // unlock operation (thread-safe)
        boolean unlock(String name, int uid) {
            rw.writeLock().lock();
            try {
                TreeNode r = map.get(name);
                if (r == null) return false;
                if (!r.isLocked || r.lockedBy != uid) return false;
                r.isLocked = false;
                r.lockedBy = -1;
                updateParents(r.parent, -1);
                return true;
            } finally {
                rw.writeLock().unlock();
            }
        }

        // upgrade operation (thread-safe, O(h) because of counters)
        boolean upgrade(String name, int uid) {
            rw.writeLock().lock();
            try {
                TreeNode r = map.get(name);
                if (r == null) return false;
                if (r.isLocked || r.lockedDescCount == 0) return false;

                // check that all locked descendants are locked by uid
                if (!allDescLockedBy(r, uid)) return false;

                // ensure no ancestor locked
                TreeNode p = r.parent;
                while (p != null) {
                    if (p.isLocked) return false;
                    p = p.parent;
                }

                // unlock all locked descendants (do DFS; unlock nodes directly to avoid re-checking)
                unlockAllDescendantsDirect(r, uid);

                // lock current node
                r.isLocked = true;
                r.lockedBy = uid;
                updateParents(r.parent, 1);
                return true;
            } finally {
                rw.writeLock().unlock();
            }
        }

        // DFS: returns false if a locked descendant is by different uid
        private boolean allDescLockedBy(TreeNode node, int uid) {
            for (TreeNode ch : node.childs) {
                if (ch.isLocked && ch.lockedBy != uid) return false;
                if (!allDescLockedBy(ch, uid)) return false;
            }
            return true;
        }

        // DFS unlocking helper that updates counters directly. Assumes write lock held.
        private void unlockAllDescendantsDirect(TreeNode node, int uid) {
            for (TreeNode ch : node.childs) {
                unlockAllDescendantsDirect(ch, uid);
            }
            if (node.isLocked && node.lockedBy == uid) {
                node.isLocked = false;
                node.lockedBy = -1;
                updateParents(node.parent, -1);
            }
        }
    }