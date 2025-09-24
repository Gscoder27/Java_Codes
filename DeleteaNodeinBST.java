import java.util.*;

class Node {
    int val;
    Node left, right;
    Node(int item) {
        val = item;
        left = right = null;
    }
}

public class DeleteaNodeinBST {
    
    // Insert a node in BST
    public static Node insert(Node root, int key) {
        if (root == null) {
            return new Node(key);
        }
        if (key < root.val) {
            root.left = insert(root.left, key);
        } else if (key > root.val) {
            root.right = insert(root.right, key);
        }
        return root;
    }

    // Inorder traversal (for checking)
    public static void inorder(Node root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.val + " ");
            inorder(root.right);
        }
    }

    // Find inorder predecessor
    public Node iop(Node root) {
        Node temp = root.left;
        while (temp.right != null) temp = temp.right;
        return temp;
    }

    // Delete a node in BST
    public Node deleteNode(Node root, int key) {
        if (root == null) return null;

        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else {
            // Found the node to delete
            if (root.left == null && root.right == null) return null;
            else if (root.left == null) return root.right;
            else if (root.right == null) return root.left;
            else {
                Node pred = iop(root);
                root.val = pred.val;  // Copy predecessor value
                root.left = deleteNode(root.left, pred.val); // Delete predecessor
            }
        }
        return root;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DeleteaNodeinBST bst = new DeleteaNodeinBST();

        Node root = null;

        System.out.print("Enter number of nodes: ");
        int n = sc.nextInt();

        System.out.println("Enter values:");
        for (int i = 0; i < n; i++) {
            int val = sc.nextInt();
            root = insert(root, val);
        }

        System.out.print("Inorder before deletion: ");
        inorder(root);
        System.out.println();

        System.out.print("Enter value to delete: ");
        int key = sc.nextInt();
        root = bst.deleteNode(root, key);

        System.out.print("Inorder after deletion: ");
        inorder(root);
    }
}
