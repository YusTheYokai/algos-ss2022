package at.technikumwien.shortest_path;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    
    private static final String STATION_REGEX = "[a-zA-Z0-9\\.\\-/, ]+";

    public static void main(String[] args) {
        try {
            // TODO: Auf Command Line anpassen
            Map<String, Node> graph = getGraph("src/main/resources/shortest_path/public_transport_system");
            Node start = graph.get("Praterstern");
            Node end = graph.get("Oberdorfstrasse");

            start.setCost(0);
            var nodes = new ArrayList<>(graph.values());
            Collections.sort(nodes);

            for (int i = 0; i < nodes.size(); i++) {
                nodes.get(i).setVisited(true);
                for (Neighbor neighbor : nodes.get(i).getNeighbors()) {
                    if (!neighbor.getNode().isVisited()) {
                        int potential = nodes.get(i).getCost() + neighbor.getCost();
                        Line changeTo = null;

                        // es kann sein, dass umgestiegen werden muss
                        if (nodes.get(i).getPrevious() != null) {
                            Set<Line> intersectingLines = new HashSet<>(neighbor.getNode().getLines());
                            intersectingLines.retainAll(nodes.get(i).getPrevious().getFirst().getLines());
    
                            // es gibt keine Linie, die beide Station anfährt
                            // das heißt, dass ein Umstieg stattfinden muss
                            if (intersectingLines.isEmpty()) {
                                potential += 5;
                                List<Line> lineChangeTo = new ArrayList<>(neighbor.getNode().getLines());
                                lineChangeTo.retainAll(nodes.get(i).getLines());
                                changeTo = lineChangeTo.get(0);
                            }
                        }

                        if (potential < neighbor.getNode().getCost()) {
                            neighbor.getNode().setCost(potential);
                            neighbor.getNode().setPrevious(new Pair<>(nodes.get(i), changeTo));
                        }
                    }
                }

                Collections.sort(nodes);
            }

            List<Pair<Node, Line>> route = new ArrayList<>();
            route.add(new Pair<>(end, null));
            Pair<Node, Line> temp = end.getPrevious();

            while (temp != null) {
                route.add(temp);
                temp = temp.getFirst().getPrevious();
            }
            Collections.reverse(route);
            route.forEach(pair -> {
                if (pair.getFirst().equals(start)) {
                    List<Line> startLine = new ArrayList<>(start.getLines());
                    startLine.retainAll(route.get(1).getFirst().getLines());
                    System.out.printf("--- line %s ---%n", startLine.get(0).getName());
                    System.out.println("   " + pair.getFirst().getName());
                } else {
                    System.out.printf("%02d %s%n", pair.getFirst().getCost(), pair.getFirst().getName());
                    if (pair.getSecond() != null) {
                        System.out.printf("--- line change to %s ---%n", pair.getSecond().getName());
                        System.out.printf("%02d %s%n", pair.getFirst().getCost() + 5, pair.getFirst().getName());
                    }
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
