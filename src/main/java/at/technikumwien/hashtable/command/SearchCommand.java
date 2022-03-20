package at.technikumwien.hashtable.command;

import java.util.Scanner;

import at.technikumwien.hashtable.Hashtable;
import at.technikumwien.hashtable.Stock;

public class SearchCommand implements Runnable {

    private final Hashtable<String, String> abbrHashtable;
    private final Hashtable<String, Stock> stockHashtable;
    private final Scanner scanner;

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    public SearchCommand(Hashtable<String, String> abbrHashtable, Hashtable<String, Stock> stockHashtable, Scanner scanner) {
        this.abbrHashtable = abbrHashtable;
        this.stockHashtable = stockHashtable;
        this.scanner = scanner;
    }

    @Override
    public void run() {
        System.out.print("Name/Abbreviation: ");
        String key = scanner.nextLine();
        
        Stock stock;
        String abbr = abbrHashtable.get(key);
        if (abbr == null) {
            stock = stockHashtable.get(key);
        } else {
            stock = stockHashtable.get(abbr);
        }
        
        if (stock == null) {
            System.err.println("No such stock");
            return;
        }

        System.out.println(stock.getHistories().get(0));
    }
    
}
