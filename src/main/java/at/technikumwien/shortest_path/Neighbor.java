package at.technikumwien.shortest_path;

public class Neighbor {

    private final int cost;
    private final Station station;
    private final Line line;

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    public Neighbor(int cost, Station station, Line line) {
        this.cost = cost;
        this.station = station;
        this.line = line;
    }

    // //////////////////////////////////////////////////////////////////////////
    // Getter
    // //////////////////////////////////////////////////////////////////////////

    public int getCost() {
        return cost;
    }

    public Station getStation() {
        return station;
    }

    public Line getLine() {
        return line;
    }
}
