package at.technikumwien.treecheck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tree {

    private static final Pair<Boolean, List<Integer>> SEARCH_NOT_FOUND = new Pair<>(false, new ArrayList<>());
    private static final Pair<Boolean, Node> SUBTREE_NOT_FOUND = new Pair<>(false, null);

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

    // Summe

    private float sum() {
        return sumTraverse(root);
    }

    private float sumTraverse(Node node) {
        float left = 0f;
        float right = 0f;

        if (node.getRight() != null) {
            right = sumTraverse(node.getRight());
        }
        if (node.getLeft() != null) {
            left = sumTraverse(node.getLeft());
        }

        return node.getValue() + left + right;
    }

    // Anzahl

    public int count() {
        return countTraverse(root);
    }

    private int countTraverse(Node node) {
        int left = 0;
        int right = 0;

        if (node.getRight() != null) {
            right = countTraverse(node.getRight());
        }
        if (node.getLeft() != null) {
            left = countTraverse(node.getLeft());
        }

        return 1 + left + right;
    }

    // Durchschnitt

    public float avg() {
        return sum() / count();
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
    
    // Search

    public Pair<Boolean, List<Integer>> search(int value) {
        return search(value, root, SEARCH_NOT_FOUND);
    }

    private Pair<Boolean, List<Integer>> search(int value, Node node, Pair<Boolean, List<Integer>> p) {
        p.getSecond().add(node.getValue());

        if (node.getValue() == value) {
            p.setFirst(true);
            return p;
        }

        var resultLeft = SEARCH_NOT_FOUND;
        var resultRight = SEARCH_NOT_FOUND;

        if (node.getLeft() != null) {
            resultLeft = search(value, node.getLeft(), copyPair(p));
        }
        if (node.getRight() != null) {
            resultRight = search(value, node.getRight(), copyPair(p));
        }

        if (Boolean.TRUE.equals(resultLeft.getFirst())) {
            return resultLeft;
        } else if (Boolean.TRUE.equals(resultRight.getFirst())) {
            return resultRight;
        } else {
            return SEARCH_NOT_FOUND;
        }
    }

    private Pair<Boolean, List<Integer>> copyPair(Pair<Boolean, List<Integer>> pair) {
        List<Integer> copiedList = new ArrayList<>();
        copiedList.addAll(pair.getSecond());
        return new Pair<>(pair.getFirst().booleanValue(), copiedList);
    }

    public boolean contains(Tree subtree) {
        return contains(subtree.root, root);
    }

    private boolean contains(Node subtreeNode, Node parentTreeNode) {
        Node found = containsTraverse(subtreeNode.getValue(), parentTreeNode);

        if (found == null) {
            return false;
        } else if (subtreeNode.getLeft() == null && subtreeNode.getRight() == null) {
            return true;
        }

        boolean left = false;
        boolean right = false;

        if (subtreeNode.getLeft() != null && found.getLeft() != null) {
            left = contains(subtreeNode.getLeft(), found.getLeft());
        }
        if (subtreeNode.getRight() != null && found.getRight() != null) {
            right = contains(subtreeNode.getRight(), found.getRight());
        }

        return left || right;
    }

    private Node containsTraverse(int value, Node node) {
        if (node.getValue() == value) {
            return node;
        }

        Node resultLeft = null;
        Node resultRight = null;

        if (node.getLeft() != null) {
            resultLeft = containsTraverse(value, node.getLeft());
        }
        if (node.getRight() != null) {
            resultRight = containsTraverse(value, node.getRight());
        }

        if (resultLeft != null) {
            return resultLeft;
        } else if (resultRight != null) {
            return resultRight;
        } else {
            return null;
        }
    }

    // Subtree

    public Pair<Boolean, List<Integer>> subtreeOf(Tree parentTree) {
        var subtreeFlat = flat();
        var parentTreeFlat = parentTree.flat();
        parentTreeFlat.removeIf(i -> !subtreeFlat.contains(i));

        if (parentTreeFlat.size() != subtreeFlat.size()) {
            return new Pair<>(false, Collections.emptyList());
        }

        for (int i = 0; i < subtreeFlat.size(); i++) {
            if (!subtreeFlat.get(i).equals(parentTreeFlat.get(i)) ) {
                return new Pair<>(false, Collections.emptyList());
            }
        }
        return new Pair<>(true, Collections.emptyList());
    }

    private List<Integer> flat() {
        List<Integer> list = new ArrayList<>();
        flatTraverse(root, list);
        return list;
    }

    private void flatTraverse(Node node, List<Integer> list) {
        if (node.getLeft() != null) {
            flatTraverse(node.getLeft(), list);
        }
        if (node.getRight() != null) {
            flatTraverse(node.getRight(), list);
        }
        list.add(node.getValue());
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

    // //////////////////////////////////////////////////////////////////////////
    // Getter
    // //////////////////////////////////////////////////////////////////////////

    public Node getRoot() {
        return root;
    }
}
