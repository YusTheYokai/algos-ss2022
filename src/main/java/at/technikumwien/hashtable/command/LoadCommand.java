package at.technikumwien.hashtable.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

import at.technikumwien.hashtable.GsonHashtableWrapper;
import at.technikumwien.hashtable.Hashtable;
import at.technikumwien.hashtable.Stock;
import com.google.gson.Gson;

public class LoadCommand implements Runnable {

    private final Consumer<Hashtable<String, String>> setAbbrHashtable;
    private final Consumer<Hashtable<String, Stock>> setStockHashtable;
    private final Scanner scanner;
    private final Gson gson;

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    public LoadCommand(Consumer<Hashtable<String, String>> setAbbrHashtable, Consumer<Hashtable<String, Stock>> setStockHashtable, Scanner scanner, Gson gson) {
        this.setAbbrHashtable = setAbbrHashtable;
        this.setStockHashtable = setStockHashtable;
        this.scanner = scanner;
        this.gson = gson;
    }

    // //////////////////////////////////////////////////////////////////////////
    // Methoden
    // //////////////////////////////////////////////////////////////////////////

    @Override
    public void run() {
        System.out.println("Relative path to file:");
        String path = scanner.nextLine();

        try {
            List<String> lines = Files.readAllLines(Path.of(path));
            GsonHashtableWrapper wrapper = gson.fromJson(String.join("", lines), GsonHashtableWrapper.class);
            setAbbrHashtable.accept(wrapper.getAbbrHashtable());
            setStockHashtable.accept(wrapper.getStockHashtable());
        } catch (IOException e) {
            System.err.println("Error reading file");
        }
    }
}
