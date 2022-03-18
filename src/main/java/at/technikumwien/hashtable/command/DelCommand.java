package at.technikumwien.hashtable.command;

import at.technikumwien.hashtable.Hashtable;
import at.technikumwien.hashtable.Stock;

public class DelCommand implements Runnable {

    private final Hashtable<String> abbrHashtable;
    private final Hashtable<Stock> stockHashtable;
    private final String key;

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    public DelCommand(Hashtable<String> abbrHashtable, Hashtable<Stock> stockHashtable, String key) {
        this.abbrHashtable = abbrHashtable;
        this.stockHashtable = stockHashtable;
        this.key = key;
    }

    // //////////////////////////////////////////////////////////////////////////
    // Methoden
    // //////////////////////////////////////////////////////////////////////////

    @Override
    public void run() {
        String abbr = abbrHashtable.delete(key);
        if (abbr == null) {
            stockHashtable.delete(key);
        } else {
            stockHashtable.delete(abbr);
        }
    }
}
