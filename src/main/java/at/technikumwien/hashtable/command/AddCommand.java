package at.technikumwien.hashtable.command;

import java.util.Scanner;

import at.technikumwien.hashtable.Hashtable;
import at.technikumwien.hashtable.Stock;

public class AddCommand implements Runnable {

    private final Hashtable<String, String> abbrHashtable;
    private final Hashtable<String, Stock> stockHashtable;
    private final Scanner scanner;

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    public AddCommand(Hashtable<String, String> abbrHashtable, Hashtable<String, Stock> stockHashtable, Scanner scanner) {
        this.abbrHashtable = abbrHashtable;
        this.stockHashtable = stockHashtable;
        this.scanner = scanner;
    }

    // //////////////////////////////////////////////////////////////////////////
    // Methoden
    // //////////////////////////////////////////////////////////////////////////

    @Override
    public void run() {
        System.out.println("Name:");
        String name = scanner.nextLine();
        System.out.println("Abbreviation:");
        String abbreviation = scanner.nextLine();
        System.out.println("ID:");
        String id = scanner.nextLine();

        Stock stock = new Stock(id, name, abbreviation);
        abbrHashtable.add(name, abbreviation);
        stockHashtable.add(abbreviation, stock);
    }
}
