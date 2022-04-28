package at.technikumwien.treecheck;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Please provide a path to a file containing a tree!");
            System.exit(1);
        } else if (args.length > 1) {
            System.err.println("Only one path to a file containing a tree is allowed!");
            System.exit(1);
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(args[0]));
            lines.stream().map(Integer::parseInt).forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("File could not be read!");
       } catch (NumberFormatException e) {
            System.err.println("Tree contains char sequence that could not be parsed to an integer!");
       }
    }
}
