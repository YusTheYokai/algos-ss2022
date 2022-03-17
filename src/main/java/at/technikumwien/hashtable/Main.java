package at.technikumwien.hashtable;

public class Main {
    
    public static void main(String[] args) {
        Stock s = new Stock("MSFT", "Microsoft Corporation", "MSFT");
        Hashtable<Stock> stockHashtable = new Hashtable<>(2003);
        Hashtable<String> stringHashtable = new Hashtable<>(2003);
        stringHashtable.add(s.getName(), s.getAbbreviation());
        stockHashtable.add(s.getAbbreviation(), s);
        System.out.println(stockHashtable.get(s.getAbbreviation()).getName());
        System.out.println(stockHashtable.get(stringHashtable.get(s.getName())).getAbbreviation());
    }
}
