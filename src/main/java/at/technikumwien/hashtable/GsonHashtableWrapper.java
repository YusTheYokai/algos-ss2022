package at.technikumwien.hashtable;

public class GsonHashtableWrapper {
    private final Hashtable<String> abbrHashtable;
    private final Hashtable<Stock> stockHashtable;

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    public GsonHashtableWrapper(Hashtable<String> abbrHashtable, Hashtable<Stock> stockHashtable) {
        this.abbrHashtable = abbrHashtable;
        this.stockHashtable = stockHashtable;
    }
    
}
