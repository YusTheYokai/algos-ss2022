package at.technikumwien.treecheck;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        args = new String[]{"src/main/resources/treecheck/parentTree", "src/main/resources/treecheck/subtree"};

        if (args.length == 0) {
            error("Please provide a path to a file containing a tree!", 1);
            System.exit(1);
        } else if (args.length > 2) {
            error("A maximum of two paths to a file containing a tree is allowed!", 2);
        }
        
        
        try {
            List<String> lines = Files.readAllLines(Paths.get(args[0]));
            List<Integer> integers = lines.stream().map(Integer::parseInt).toList();
            Tree tree = new Tree(integers);
            
            if (args.length == 1) {
                tree.print();
                System.out.println("AVL: " + (tree.isAvl() ? "yes" : "no"));
                System.out.println("min: " + tree.min() + ", max: " + tree.max() + ", avg: " + tree.avg());
            } else {
                lines = Files.readAllLines(Paths.get(args[1]));
                integers = lines.stream().map(Integer::parseInt).toList();
                Tree subtree = new Tree(integers);
                System.out.println(tree.isSubtree(subtree));
            }
            
        } catch (IOException e) {
            error("File could not be read!", 3);
       } catch (NumberFormatException e) {
            error("Tree contains char sequence that could not be parsed to an integer!", 4);
       }
    }

    private static void error(String message, int code) {
        System.err.println(message);
        System.exit(code);
    }
}
