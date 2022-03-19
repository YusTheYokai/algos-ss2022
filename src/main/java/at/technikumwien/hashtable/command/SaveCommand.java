package at.technikumwien.hashtable.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

import at.technikumwien.hashtable.GsonHashtableWrapper;
import at.technikumwien.hashtable.Hashtable;
import at.technikumwien.hashtable.Stock;
import com.google.gson.Gson;

public class SaveCommand implements Runnable {

    private final Hashtable<String, String> abbrHashtable;
    private final Hashtable<String, Stock> stockHashtable;
    private final Scanner scanner;
    private final Gson gson;

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    public SaveCommand(Hashtable<String, String> abbrHashtable, Hashtable<String, Stock> stockHashtable, Scanner scanner,Gson gson) {
        this.abbrHashtable = abbrHashtable;
        this.stockHashtable = stockHashtable;
        this.scanner = scanner;
        this.gson = gson;
    }

    // //////////////////////////////////////////////////////////////////////////
    // Methoden
    // //////////////////////////////////////////////////////////////////////////

    @Override
    public void run() {
        System.out.println("New file name (extention excluded):");
        String filename = scanner.nextLine();
        String json = gson.toJson(new GsonHashtableWrapper(abbrHashtable, stockHashtable));

        try {
            Files.write(Path.of(filename + ".json"), json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error writing file");
        }
    }
}
