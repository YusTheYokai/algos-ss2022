package at.technikumwien.hashtable;

import java.time.LocalDate;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.indvd00m.ascii.render.Render;
import com.indvd00m.ascii.render.api.ICanvas;
import com.indvd00m.ascii.render.api.IContextBuilder;
import com.indvd00m.ascii.render.api.IRender;
import com.indvd00m.ascii.render.elements.PseudoText;

import at.technikumwien.hashtable.command.AddCommand;
import at.technikumwien.hashtable.command.DelCommand;
import at.technikumwien.hashtable.command.ImportCommand;
import at.technikumwien.hashtable.command.LoadCommand;
import at.technikumwien.hashtable.command.PlotCommand;
import at.technikumwien.hashtable.command.QuitCommand;
import at.technikumwien.hashtable.command.SaveCommand;
import at.technikumwien.hashtable.command.SearchCommand;
import at.technikumwien.hashtable.command.UnknownCommand;

public class Main {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

    private static Hashtable<String, String> abbrHashtable = new Hashtable<>(2003);
    private static Hashtable<String, Stock> stockHashtable = new Hashtable<>(2003);
    private static boolean quit = false;

    public static void main(String[] args) {
        IRender render = new Render();
        IContextBuilder builder = render.newBuilder();
        builder.width(80).height(15);
        builder.element(new PseudoText("Stocktable"));
        ICanvas canvas = render.render(builder.build());
        System.out.println(canvas.getText());

        final Scanner scanner = new Scanner(System.in);

        while (!quit) {
            System.out.print("\nCommand: ");
            Runnable command = new UnknownCommand();
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("add")) {
                command = new AddCommand(abbrHashtable, stockHashtable, scanner);
            } else if (input.equalsIgnoreCase("quit")) {
                command = new QuitCommand(Main::setQuit);
            } else if (input.equalsIgnoreCase("del")) {
                command = new DelCommand(abbrHashtable, stockHashtable, scanner);
            } else if (input.equalsIgnoreCase("import")) {
                command = new ImportCommand(abbrHashtable, stockHashtable, scanner);
            } else if (input.equalsIgnoreCase("save")) {
                command = new SaveCommand(abbrHashtable, stockHashtable, scanner, gson);
            } else if (input.equalsIgnoreCase("load")) {
                command = new LoadCommand(Main::setAbbrHashtable, Main::setStockHashtable, scanner, gson);
            } else if (input.equalsIgnoreCase("search")) {
                command = new SearchCommand(abbrHashtable, stockHashtable, scanner);
            } else if (input.equalsIgnoreCase("plot")) {
                command = new PlotCommand(abbrHashtable, stockHashtable, scanner);
            }
            command.run();
        }

        scanner.close();
    }

    private static void setAbbrHashtable(Hashtable<String, String> ah) {
        abbrHashtable = ah;
    }

    private static void setStockHashtable(Hashtable<String, Stock> sh) {
        stockHashtable = sh;
    }

    private static void setQuit(boolean q) {
        quit = q;
    }
}
