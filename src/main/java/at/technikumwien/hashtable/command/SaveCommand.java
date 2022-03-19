package at.technikumwien.hashtable.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import at.technikumwien.hashtable.GsonHashtableWrapper;
import at.technikumwien.hashtable.Hashtable;
import at.technikumwien.hashtable.LocalDateAdapter;
import at.technikumwien.hashtable.Stock;

public class SaveCommand implements Runnable {

    private final Hashtable<String> abbrHashtable;
    private final Hashtable<Stock> stockHashtable;
    private final Scanner scanner;

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    public SaveCommand(Hashtable<String> abbrHashtable, Hashtable<Stock> stockHashtable, Scanner scanner) {
        this.abbrHashtable = abbrHashtable;
        this.stockHashtable = stockHashtable;
        this.scanner = scanner;
    }


    // //////////////////////////////////////////////////////////////////////////
    // Methoden
    // //////////////////////////////////////////////////////////////////////////

    @Override
    public void run() {
        System.out.println("New file name (extention excluded):");
        String filename = scanner.nextLine();

        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        String json = gson.toJson(new GsonHashtableWrapper(abbrHashtable, stockHashtable));

        try {
            Files.write(Path.of(filename + ".json"), json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error writing file.");
        }
    }
}
