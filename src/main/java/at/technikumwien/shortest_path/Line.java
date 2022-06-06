package at.technikumwien.shortest_path;

import java.util.ArrayList;
import java.util.List;

public class Line {

    private final String name;
    private final List<Commute> commutes = new ArrayList<>();

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    public Line(String name) {
        this.name = name;
    }

    // //////////////////////////////////////////////////////////////////////////
    // Getter und Setter
    // //////////////////////////////////////////////////////////////////////////

    public String getName() {
        return name;
    }

    public List<Commute> getCommutes() {
        return commutes;
    }
}
