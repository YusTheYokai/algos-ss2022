package at.technikumwien.hashtable;

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
    }
}
