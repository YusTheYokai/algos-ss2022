package at.technikumwien.treecheck;

import java.util.ArrayList;
import java.util.List;

/**
 * Stellt ein {@link Node}gespann dar.
 */
public class Tree {

    private static final Pair<Boolean, List<Integer>> SEARCH_NOT_FOUND = new Pair<>(false, new ArrayList<>());

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

    // /////////////////////////////////////
    // Ausgeben
    // /////////////////////////////////////

    /**
     * Gibt den Tree aus.
     */
    public void print() {
        printTraverse(root);
    }

    /**
     * Gibt ein {@link Node}gespann aus.
     * @param node das {@link Node}gespann
     */
    private void printTraverse(Node node) {
        if (node.getRight() != null) {
            printTraverse(node.getRight());
        }
        if (node.getLeft() != null) {
            printTraverse(node.getLeft());
        }

        final int bal = node.balance();
        final boolean violation = bal > 1;
        System.out.println("bal(" + node.getValue() + ") = " + bal + (violation ? " (AVL violation!)" : "")); // NOSONAR
    }

    // /////////////////////////////////////
    // Hinzufügen
    // /////////////////////////////////////

    /**
     * Fügt einen Wert dem Tree hinzu.
     * @param value der Wert
     */
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

    /**
     * Fügt einem {@link Node}gespann einen Wert hinzu. 
     * @param node das {@link Node}gespann
     * @param value der Wert
     */
    private static void addTraverse(Node node, int value) {
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

    /**
     * Prüft, ob der Tree ein AVL-Tree ist.
     * @return {@code true}, wenn ja, {@code false}, wenn nicht.
     */
    public boolean isAvl() {
        return isAvlTraverse(root);
    }

    /**
     * Prüft, ob ein {@link Node}gespann ein AVL-Tree ist.
     * @param node das {@link Node}gespann
     * @return {@code true}, wenn ja, {@code false}, wenn nicht.
     */
    private static boolean isAvlTraverse(Node node) {
        boolean violationRight = false;
        boolean violationLeft = false;

        if (node.getRight() != null) {
            violationRight = isAvlTraverse(node.getRight());
        }
        if (node.getLeft() != null) {
            violationLeft = isAvlTraverse(node.getLeft());
        }

        return node.balance() < 2 && !violationRight && !violationLeft;
    }

    // /////////////////////////////////////
    // Minimum
    // /////////////////////////////////////

    /**
     * Findet das Minmum des Trees.
     * @return das Minimum
     */
    public int min() {
        return minTraverse(root);
    }

    /**
     * Findet das Minmum in einem {@link Node}gespann.
     * @param node das {@link Node}gespann
     * @return das Minimum
     */
    private static int minTraverse(Node node) {
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

    /**
     * Findet das Maximum des Trees.
     * @return das Maximum
     */
    public int max() {
        return maxTraverse(root);
    }

    /**
     * Findet das Maximum in einem {@link Node}gespann.
     * @param node das {@link Node}gespann
     * @return das Maximum
     */
    private static int maxTraverse(Node node) {
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

    // /////////////////////////////////////
    // Summe
    // /////////////////////////////////////

    /**
     * Berechnet die Summe des Trees.
     * @return die Summe
     */
    private float sum() {
        return sumTraverse(root);
    }

    /**
     * Berechnet die Summe eines {@link Node}gespanns.
     * @param node das {@link Node}gespann
     * @return die Summe
     */
    private static float sumTraverse(Node node) {
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

    // /////////////////////////////////////
    // Anzahl
    // /////////////////////////////////////

    /**
     * Zählt die Anzahl der Werte im Tree.
     * @return die Anzahl
     */
    public int count() {
        return countTraverse(root);
    }

    /**
     * Zählt die Anzahl der Werte in einem {@link Node}gespann.
     * @param node das {@link Node}gespann
     * @return die Anzahl
     */
    private static int countTraverse(Node node) {
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

    // /////////////////////////////////////
    // Durchschnitt
    // /////////////////////////////////////

    /**
     * Berechnet den Durchschnitt des Trees.
     * @return den Durchschnitt
     * @see {@link #sum()} {@link #count()}
     */
    public float avg() {
        return sum() / count();
    }

    // /////////////////////////////////////
    // Suchen
    // /////////////////////////////////////

    /**
     * Sucht einen Wert im Tree.
     * @param value der Wert
     * @return ein {@link Pair} mit einem Boolean, ob der Wert gefunden wurde
     * und einer Liste aller Werte die auf dem Weg zum gesuchten Werte traversiert wurden.
     */
    public Pair<Boolean, List<Integer>> search(int value) {
        return search(value, root, SEARCH_NOT_FOUND);
    }

    /**
     * Prüft, ob sich ein Wert in einem {@link Node}gespann befindet.
     * @param value der Wert
     * @param node das {@link Node}gespann
     * @param pair das Ergebnis, welches auch zurückgegeben wird. Hier wird gespeichert,
     * ob die Suche erfolgreich war und alle Werte die bis zum gefundnen Wert traversiert wurden.
     * @return das pair
     */
    private Pair<Boolean, List<Integer>> search(int value, Node node, Pair<Boolean, List<Integer>> pair) {
        pair.getSecond().add(node.getValue());

        if (node.getValue() == value) {
            pair.setFirst(true);
            return pair;
        }

        var resultLeft = SEARCH_NOT_FOUND;
        var resultRight = SEARCH_NOT_FOUND;

        if (node.getLeft() != null) {
            resultLeft = search(value, node.getLeft(), copyPair(pair));
        }
        if (node.getRight() != null) {
            resultRight = search(value, node.getRight(), copyPair(pair));
        }

        if (Boolean.TRUE.equals(resultLeft.getFirst())) {
            return resultLeft;
        } else if (Boolean.TRUE.equals(resultRight.getFirst())) {
            return resultRight;
        } else {
            return SEARCH_NOT_FOUND;
        }
    }

    /**
     * Erstellt eine Kopie eines {@link Pair}s.
     * @param pair das {@link Pair}
     * @return die Kopie
     */
    private Pair<Boolean, List<Integer>> copyPair(Pair<Boolean, List<Integer>> pair) {
        List<Integer> copiedList = new ArrayList<>();
        copiedList.addAll(pair.getSecond());
        return new Pair<>(pair.getFirst().booleanValue(), copiedList);
    }

    // /////////////////////////////////////
    // Subtree
    // /////////////////////////////////////

    /**
     * Prüft, ob ein {@link Tree} ein Subtree ist.
     * @param subtree der {@link Tree}
     * @return {@code true}, wenn es ein Subtree ist, {@code false}, wenn nicht.
     */
    public boolean contains(Tree subtree) {
        return contains(subtree.root, root);
    }

    /**
     * Prüft, ob sich das {@link Node}gespann einer Subtree-Node
     * im {@link Node}gespann einer Parent-Tree-Node befindet.
     * @param subtreeNode die Subtree-Node
     * @param parentTreeNode die Parent-Tree-Node
     * @return {@code true}, wenn sich das Subtree-Nodegespann im Parent-Tree-Nodegespann befindet, {@code false}, wenn nicht.
     */
    private boolean contains(Node subtreeNode, Node parentTreeNode) {
        Node found = containsTraverse(subtreeNode.getValue(), parentTreeNode);

        if (found == null) {
            return false;
        }

        boolean left = false;
        boolean right = false;

        if (subtreeNode.getLeft() == null) {
            left = true;
        } else if (found.getLeft() != null) {
            left = contains(subtreeNode.getLeft(), found.getLeft());
        }

        if (subtreeNode.getRight() == null) {
            right = true;
        } else if (found.getRight() != null) {
            right = contains(subtreeNode.getRight(), found.getRight());
        }

        return left && right;
    }

    /**
     * Prüft, ob sich ein Wert in einem {@link Node}gespann befindet.
     * @param value der Wert
     * @param node das {@link Node}gespann
     * @return die {@link Node}, dessen Wert gesucht wurde.
     * {@code null}, wenn der Wert nicht gefunden wurde.
     */
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

    // //////////////////////////////////////////////////////////////////////////
    // Getter
    // //////////////////////////////////////////////////////////////////////////

    public Node getRoot() {
        return root;
    }
}
