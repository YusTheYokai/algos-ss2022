package at.technikumwien.shortest_path;

import java.util.HashSet;
import java.util.Set;

public class Node implements Comparable<Node> {

    private final String name;
    // TODO: Anstatt mehrere Linien in einer Node zu speichern gibt es für jede Linie die eine Station anfährt eine Node
    // dann musst beim Erstellen der Verbindungen zwischen den gleichen Station aber verschiedener Linien die Cost von 5 eingestellt werden
    private final Set<Line> lines = new HashSet<>();
    private int cost = Integer.MAX_VALUE;
    private Set<Neighbor> neighbors = new HashSet<>();
    private Node previous;
    private boolean visited = false;

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    public Node(String name, Line line) {
        this.name = name;
        lines.add(line);
    }

    // //////////////////////////////////////////////////////////////////////////
    // Methoden
    // //////////////////////////////////////////////////////////////////////////

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        return hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public int compareTo(Node o) {
        if (visited) {
            return Integer.MAX_VALUE;
        } else {
            return Integer.compare(cost, o.cost);
        }
    }

    // //////////////////////////////////////////////////////////////////////////
    // Getter und Setter
    // //////////////////////////////////////////////////////////////////////////

    public String getName() {
        return name;
    }

    public Set<Line> getLines() {
        return lines;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Set<Neighbor> getNeighbors() {
        return neighbors;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
