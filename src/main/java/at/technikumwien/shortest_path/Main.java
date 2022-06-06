package at.technikumwien.shortest_path;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    
    private static final String STATION_REGEX = "[a-zA-Z0-9\\.\\-/, ]+";

    public static void main(String[] args) {
        try {
            // TODO: Auf Command Line anpassen
            Map<String, Node> graph = getGraph("src/main/resources/shortest_path/public_transport_system");
            Node start = graph.get("Westbahnhof");
            Node end = graph.get("Rodaun");

            start.setCost(0);
            var nodes = new ArrayList<>(graph.values());
            Collections.sort(nodes);

            for (int i = 0; i < nodes.size(); i++) {
                nodes.get(i).setVisited(true);
                for (Neighbor neighbor : nodes.get(i).getNeighbors()) {
                    if (!neighbor.getTo().isVisited()) {
                        int potential = nodes.get(i).getCost() + neighbor.getCost();
                        if (neighbor.isLineChange()) {
                            potential += 5;
                        }

                        if (potential < neighbor.getTo().getCost()) {
                            neighbor.getTo().setCost(potential);
                            neighbor.getTo().setPrevious(nodes.get(i));
                        }
                    }
                }

                Collections.sort(nodes);
            }

            List<Node> route = new ArrayList<>();
            while (end != null) {
                route.add(end);
                end = end.getPrevious();
            }
            Collections.reverse(route);
            route.forEach(node -> {
                if (node.equals(start)) {
                    System.out.println("   " + node.getName());
                } else {
                    System.out.printf("%02d %s\n", node.getCost(), node.getName());
                }
            });
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private static List<Line> getLines(String filePath) throws IOException {
        List<String> publicTransportLines = Files.readAllLines(Paths.get(filePath));
        List<Line> lines = new ArrayList<>();

        Pattern lineCommutesPattern = Pattern.compile("(?=(\"" + STATION_REGEX + "\" \\d \"" + STATION_REGEX + "\"))");

        for (String publicTransportLine : publicTransportLines) {
            String[] split = publicTransportLine.split(": ");

            Line l = new Line(split[0]);
            lines.add(l);

            Matcher lineCommutesMatcher = lineCommutesPattern.matcher(split[1]);

            Pattern commutPattern = Pattern.compile("\"(" + STATION_REGEX + ")\" (\\d) \"(" + STATION_REGEX + ")\"");
            while (lineCommutesMatcher.find()) {
                Matcher commuteMatcher = commutPattern.matcher(lineCommutesMatcher.group(1));
                commuteMatcher.find();
                l.getCommutes().add(new Commute(commuteMatcher.group(1), Integer.parseInt(commuteMatcher.group(2)), commuteMatcher.group(3)));
            }
        }

        return lines;
    }

    private static Map<String, Node> getGraph(String filePath) throws IOException {
        List<Line> lines = getLines(filePath);
        Map<String, Node> nodes = new HashMap<>();

        for (Line line : lines) {
            for (Commute commute : line.getCommutes()) {
                Node s1 = nodes.get(commute.getStation1());
                Node s2 = nodes.get(commute.getStation2());

                if (s1 == null) {
                    s1 = new Node(commute.getStation1(), line);
                    nodes.put(commute.getStation1(), s1);
                } else {
                    s1.getLines().add(line);
                }

                if (s2 == null) {
                    s2 = new Node(commute.getStation2(), line);
                    nodes.put(commute.getStation2(), s2);
                } else {
                    s2.getLines().add(line);
                }

                s1.getNeighbors().add(new Neighbor(commute.getTime(), s2));
                s2.getNeighbors().add(new Neighbor(commute.getTime(), s1));
            }
        }

        return nodes;
    }
}
