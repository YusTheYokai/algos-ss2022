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
    // Methoden
    // //////////////////////////////////////////////////////////////////////////

    // @Override
    // public boolean equals(Object obj) {
    //     if (obj == null) {
    //         return false;
    //     }

    //     return hashCode() == obj.hashCode();
    // }

    // @Override
    // public int hashCode() {
    //     return station.hashCode();
    // }

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
