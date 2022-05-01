package at.technikumwien.treecheck;

/**
 * Node eines Trees.
 */
public class Node {

    private int value;
    private Node left;
    private Node right;

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    public Node(int value) {
        this.value = value;
    }

    // //////////////////////////////////////////////////////////////////////////
    // Methoden
    // //////////////////////////////////////////////////////////////////////////

    /**
     * Berechnet die Balance der Node.
     * @return die Balance
     */
    public int balance() {
        return Math.abs(height(left) - height(right));
    }

    /**
     * Berechnet die Balance einer Node.
     * @param node die Node
     * @return die Balance
     */
    private static int height(Node node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + Integer.max(height(node.left), height(node.right));
        }
    }

    // //////////////////////////////////////////////////////////////////////////
    // Getter
    // //////////////////////////////////////////////////////////////////////////

    public int getValue() {
        return value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
