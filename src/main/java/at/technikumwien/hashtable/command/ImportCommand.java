package at.technikumwien.hashtable.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import at.technikumwien.hashtable.Hashtable;
import at.technikumwien.hashtable.History;
import at.technikumwien.hashtable.Stock;

public class ImportCommand implements Runnable {

    private final Hashtable<String> abbrHashtable;
    private final Hashtable<Stock> stockHashtable;
    private final Scanner scanner;

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    public ImportCommand(Hashtable<String> abbrHashtable, Hashtable<Stock> stockHashtable, Scanner scanner) {
        this.abbrHashtable = abbrHashtable;
        this.stockHashtable = stockHashtable;
        this.scanner = scanner;
    }

    // //////////////////////////////////////////////////////////////////////////
    // Methoden
    // //////////////////////////////////////////////////////////////////////////

    @Override
    public void run() {
        Stock stock = askForStock();
        if (stock == null) {
            System.err.println("Stock does not exist!");
            return;
        }

        try {
            List<String> lines = askForFile();
            List<History> histories = linesToHistories(lines);
            histories.addAll(stock.getHistories());
            stock.setHistories(histories.stream().sorted().distinct().limit(30).toList());
        } catch (IOException e) {
            System.err.println("Error reading file");
        } catch (NumberFormatException e) {
            System.err.println("Ill-formatted file");
        }
    }

    private Stock askForStock() {
        System.out.println("For stock (name/abbreviation):");
        String key = scanner.nextLine();

        String abbr = abbrHashtable.get(key);
        return abbr == null ? stockHashtable.get(key) : stockHashtable.get(abbr);
    }

    private List<String> askForFile() throws IOException {
        System.out.println("Relative path to file:");
        String path = scanner.nextLine();
        return Files.readAllLines(Path.of(path));
    }

    private List<History> linesToHistories(List<String> lines) throws NumberFormatException {
        List<History> histories = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            String[] values = lines.get(i).split(",");
            histories.add(new History(
                LocalDate.parse(values[0], DateTimeFormatter.ISO_LOCAL_DATE),
                Double.parseDouble(values[1]),
                Double.parseDouble(values[2]),
                Double.parseDouble(values[3]),
                Double.parseDouble(values[4]),
                Double.parseDouble(values[5]),
                Long.parseLong(values[6]))
            );
        }
        return histories;
    }
}
