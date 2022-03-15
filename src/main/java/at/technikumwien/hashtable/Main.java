package at.technikumwien.hashtable;

public class Main {
    
    public static void main(String[] args) {
        Stock s = new Stock("MSFT", "Microsoft Corporation", "MSFT");
        Hashtable h = new Hashtable(2003);
        h.add(s);
        System.out.println(h.get(s.getName()).getAbbreviation());
    }
}
