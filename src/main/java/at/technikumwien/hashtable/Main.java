package at.technikumwien.hashtable;

import java.util.Scanner;

import at.technikumwien.hashtable.command.AddCommand;
import at.technikumwien.hashtable.command.DelCommand;
import at.technikumwien.hashtable.command.ImportCommand;
import at.technikumwien.hashtable.command.QuitCommand;
import at.technikumwien.hashtable.command.SaveCommand;
import at.technikumwien.hashtable.command.UnknownCommand;

public class Main {

    private static boolean quit = false;

    public static void main(String[] args) {
        final Hashtable<String> abbrHashtable = new Hashtable<>(2003);
        final Hashtable<Stock> stockHashtable = new Hashtable<>(2003);
        final Scanner scanner = new Scanner(System.in);

        while (!quit) {
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
                command = new SaveCommand(abbrHashtable, stockHashtable, scanner);
            }
            command.run();
        }

        scanner.close();
    }

    private static void setQuit(boolean q) {
        quit = q;
    }
}
