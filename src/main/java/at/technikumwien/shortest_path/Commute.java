package at.technikumwien.shortest_path;

public class Commute {
    
    private final String station1;
    private final int time;
    private final String station2;

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    public Commute(String station1, int time, String station2) {
        this.station1 = station1;
        this.time = time;
        this.station2 = station2;
    }

    // //////////////////////////////////////////////////////////////////////////
    // Getter und Setter
    // //////////////////////////////////////////////////////////////////////////

    public String getStation1() {
        return station1;
    }

    public int getTime() {
        return time;
    }

    public String getStation2() {
        return station2;
    }
}
