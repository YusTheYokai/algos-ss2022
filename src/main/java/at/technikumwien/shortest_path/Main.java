package at.technikumwien.shortest_path;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    
    public static void main(String[] args) {
        args = new String[]{"src/main/resources/shortest_path/public_transport_system"};

        try {
            List<String> publicTransportLines = Files.readAllLines(Paths.get(args[0]));
            List<Line> lines = new ArrayList<>();

            Pattern lineCommutesPattern = Pattern.compile("(?=(\"[a-zA-Z0-9_\\-/ ]+\" \\d \"[a-zA-Z0-9_\\-/ ]+\"))");

            for (String publicTransportLine : publicTransportLines) {
                String[] split = publicTransportLine.split(": ");

                Line l = new Line(split[0]);
                lines.add(l);

                Matcher lineCommutesMatcher = lineCommutesPattern.matcher(split[1]);

                Pattern commutPattern = Pattern.compile("\"([a-zA-Z0-9_\\-/ ]+)\" (\\d) \"([a-zA-Z0-9_\\-/ ]+)\"");
                while (lineCommutesMatcher.find()) {
                    Matcher commuteMatcher = commutPattern.matcher(lineCommutesMatcher.group(1));
                    commuteMatcher.find();
                    l.getCommutes().add(new Commute(commuteMatcher.group(1), Integer.parseInt(commuteMatcher.group(2)), commuteMatcher.group(3)));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
