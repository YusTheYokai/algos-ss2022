package at.technikumwien.shortest_path;

import java.util.HashSet;
import java.util.Set;

public class Station implements Comparable<Station> {

    private final String name;
    private final Line line;
    private final Set<Neighbor> neighbors = new HashSet<>();
    private int cost = Integer.MAX_VALUE;
    private Station previous;

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    public Station(String name, Line line) {
        this.name = name;
        this.line = line;
    }

    // //////////////////////////////////////////////////////////////////////////
    // Methoden
    // //////////////////////////////////////////////////////////////////////////

    public void addNeighbor(Neighbor neighbor) {
        neighbors.add(neighbor);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Station o) { // NOSONAR
        return Integer.compare(cost, o.cost);
    }

    // //////////////////////////////////////////////////////////////////////////
    // Getter und Setter
    // //////////////////////////////////////////////////////////////////////////

    public String getName() {
        return name;
    }

    public Line getLine() {
        return line;
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

    public Station getPrevious() {
        return previous;
    }

    public void setPrevious(Station previous) {
        this.previous = previous;
    }
}
