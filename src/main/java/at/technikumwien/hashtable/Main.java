package at.technikumwien.hashtable;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Stock s = new Stock("MSFT", "Microsoft Corporation", "MSFT");
        Hashtable<Stock> stockHashtable = new Hashtable<>(2003);
        Hashtable<String> stringHashtable = new Hashtable<>(2003);

        // Mapped den Namen auf das Kürzel
        //            Microsoft  -> MSFT
        stringHashtable.add(s.getName(), s.getAbbreviation());

        // Mapped das Kürzel auf das Stock Objekt
        //            MSFT        -> s
        stockHashtable.add(s.getAbbreviation(), s);

        System.out.println(stockHashtable.get(s.getAbbreviation()).getName());
        System.out.println(stockHashtable.get(stringHashtable.get(s.getName())).getAbbreviation());

        stockHashtable.delete(stringHashtable.delete(s.getName()));
        System.out.println(stringHashtable.get(s.getName()));
        System.out.println(stockHashtable.get(s.getAbbreviation()));

        /////////////////////////////////////////////////////////////////////////////////////

        Hashtable<String> hashtable = new Hashtable<>(4);
        hashtable.add("test", "string1");
        hashtable.add("key1", "meineKatze");

        /////////////////////////////////////////////////////////////////////////////////////

        Scanner scanner = new Scanner(System.in);

        boolean con = true;
        while (con) {
            System.out.println(":");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("quit")) {
                con = false;
            } else {
                System.out.println("Such a command does not exist.");
            }
        }

        scanner.close();
    }
}
