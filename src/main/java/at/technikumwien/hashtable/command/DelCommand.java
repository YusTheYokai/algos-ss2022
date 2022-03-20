package at.technikumwien.hashtable.command;

import java.util.Scanner;

import at.technikumwien.hashtable.Hashtable;
import at.technikumwien.hashtable.Stock;

public class DelCommand implements Runnable {

    private final Hashtable<String, String> abbrHashtable;
    private final Hashtable<String, Stock> stockHashtable;
    private final Scanner scanner;

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    public DelCommand(Hashtable<String, String> abbrHashtable, Hashtable<String, Stock> stockHashtable, Scanner scanner) {
        this.abbrHashtable = abbrHashtable;
        this.stockHashtable = stockHashtable;
        this.scanner = scanner;
    }

    // //////////////////////////////////////////////////////////////////////////
    // Methoden
    // //////////////////////////////////////////////////////////////////////////

    @Override
    public void run() {
        System.out.println("Name/Abbreviation:");
        String key = scanner.nextLine();

        String abbr = abbrHashtable.delete(key);
        if (abbr == null) {
            Stock s = stockHashtable.delete(key);
            if (s == null) {
                System.err.println("No such stock");
            } else {
                abbrHashtable.delete(stockHashtable.delete(key).getName());
            }
        } else {
            stockHashtable.delete(abbr);
        }
    }
}
