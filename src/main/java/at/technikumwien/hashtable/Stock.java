package at.technikumwien.hashtable;

public class Stock {

    private final String id;
    private final String name;
    private final String abbreviation;
    private final History[] histories = new History[30];

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    public Stock(String id, String name, String abbreviation) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
    }

    // //////////////////////////////////////////////////////////////////////////
    // Methoden
    // //////////////////////////////////////////////////////////////////////////

    public static int hash(String name) {
        return name.hashCode();
    }
    
    @Override
    public int hashCode() {
        return hash(name);
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

    public History[] getHistories() {
        return histories;
    }
}
