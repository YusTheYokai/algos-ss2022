package at.technikumwien.hashtable;

import java.util.Scanner;

import at.technikumwien.hashtable.command.AddCommand;
import at.technikumwien.hashtable.command.QuitCommand;
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
            }
            command.run();
        }

        scanner.close();
    }

    private static void setQuit(boolean q) {
        quit = q;
    }
}
