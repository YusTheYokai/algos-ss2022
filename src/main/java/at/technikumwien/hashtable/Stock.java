package at.technikumwien.hashtable;

import java.util.ArrayList;
import java.util.List;

public class Stock {

    private final String id;
    private final String name;
    private final String abbreviation;
    private List<History> histories = new ArrayList<>();

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    public Stock(String id, String name, String abbreviation) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
    }

    // //////////////////////////////////////////////////////////////////////////
    // Getter
    // //////////////////////////////////////////////////////////////////////////

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }
}
