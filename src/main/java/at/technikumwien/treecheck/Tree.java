package at.technikumwien.treecheck;

import java.util.List;

public class Tree {

    private Node root;

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    public Tree(List<Integer> integers) {
        integers.stream().forEach(this::add);
    }

    // //////////////////////////////////////////////////////////////////////////
    // Methoden
    // //////////////////////////////////////////////////////////////////////////

    // Hinzufügen

    public void add(int value) {
        if (root == null) {
            root = new Node(value);
        } else {
            try {
                addTraverse(root, value);
            } catch (IllegalArgumentException e) {
                // Doppelter Wert im Baum gefunden, wird ignoriert.
            }
        }
    }

    private void addTraverse(Node node, int value) {
        if (node.getValue() == value) {
            throw new IllegalArgumentException();
        } else if (node.getValue() < value) {
            if (node.getRight() == null) {
                node.setRight(new Node(value));
            } else {
                addTraverse(node.getRight(), value);
            }
        } else {
            if (node.getLeft() == null) {
                node.setLeft(new Node(value));
            } else {
                addTraverse(node.getLeft(), value);
            }
        }
    }

    // Prüfung AVL

    public boolean isAvl() {
        return isAvlTraverse(root);
    }

    private boolean isAvlTraverse(Node node) {
        boolean violationRight = false;
        boolean violationLeft = false;

        if (node.getRight() != null) {
            violationRight = isAvlTraverse(node.getRight());
        }
        if (node.getLeft() != null) {
            violationLeft = isAvlTraverse(node.getLeft());
        }

        return node.bal() < 2 && !violationRight && !violationLeft;
    }

    // Minimum

    public int min() {
        return minTraverse(root);
    }

    private int minTraverse(Node node) {
        int minRight = Integer.MAX_VALUE;
        int minLeft = Integer.MAX_VALUE;

        if (node.getRight() != null) {
            minRight = minTraverse(node.getRight());
        }
        if (node.getLeft() != null) {
            minLeft = minTraverse(node.getLeft());
        }

        return Math.min(Math.min(minRight, minLeft), node.getValue());
    }

    // Maximum

    public int max() {
        return maxTraverse(root);
    }

    private int maxTraverse(Node node) {
        int maxRight = Integer.MIN_VALUE;
        int maxLeft = Integer.MIN_VALUE;

        if (node.getRight() != null) {
            maxRight = maxTraverse(node.getRight());
        }
        if (node.getLeft() != null) {
            maxLeft = maxTraverse(node.getLeft());
        }

        return Math.max(Math.max(maxRight, maxLeft), node.getValue());
    }

    // Durchschnitt

    public float avg() {
        Pair<Float, Integer> pair = new Pair<>(0f, 0);
        avgTraverse(root, pair);
        return pair.getFirst() / pair.getSecond();
    }

    private void avgTraverse(Node node, Pair<Float, Integer> pair) {
        if (node.getRight() != null) {
            avgTraverse(node.getRight(), pair);
        }
        if (node.getLeft() != null) {
            avgTraverse(node.getLeft(), pair);
        }

        pair.setFirst(pair.getFirst() + node.getValue());
        pair.setSecond(pair.getSecond() + 1);
    }

    // Ausgeben

    public void print() {
        printTraverse(root);
    }

    private void printTraverse(Node node) {
        if (node.getRight() != null) {
            printTraverse(node.getRight());
        }
        if (node.getLeft() != null) {
            printTraverse(node.getLeft());
        }

        final int bal = node.bal();
        final boolean violation = bal > 1;
        System.out.println("bal(" + node.getValue() + ") = " + bal + (violation ? " (AVL violation!)" : ""));
    }
    
    public boolean isSubtree(Tree subtree) {
        return isSubtreeTraverse(root, subtree.root);
    }

    private boolean isSubtreeTraverse(Node n1, Node n2) {
        if (n2 == null) {
            return true;
        }
        
        if (n1 == null) {
            return false;
        }
        
        if (equalsTraverse(n1, n2)) {
            return true;
        }

        return isSubtreeTraverse(n1.getLeft(), n2) || isSubtreeTraverse(n1.getRight(), n2);
    }

    private boolean equalsTraverse(Node n1, Node n2) {
        if (n1 == null && n2 == null) {
            return true;
        } 
        
        if (n1 == null || n2 == null) {
            return false;
        }

        return n1.getValue() == n2.getValue() &&
                equalsTraverse(n1.getLeft(), n2.getLeft()) &&
                equalsTraverse(n1.getRight(), n2.getRight());
    }
}
