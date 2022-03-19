package at.technikumwien.hashtable;

public class GsonHashtableWrapper {

    private final Hashtable<String, String> abbrHashtable;
    private final Hashtable<String, Stock> stockHashtable;

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    public GsonHashtableWrapper(Hashtable<String, String> abbrHashtable, Hashtable<String, Stock> stockHashtable) {
        this.abbrHashtable = abbrHashtable;
        this.stockHashtable = stockHashtable;
    }
    
    // //////////////////////////////////////////////////////////////////////////
    // Getter
    // //////////////////////////////////////////////////////////////////////////

    public Hashtable<String, String> getAbbrHashtable() {
        return abbrHashtable;
    }

    public Hashtable<String, Stock> getStockHashtable() {
        return stockHashtable;
    }
}
