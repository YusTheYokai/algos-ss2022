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
            Map<String, Station> graph = getGraph("src/main/resources/shortest_path/public_transport_system");
            // Station start = graph.get("Floridsdorf");
            // Station end = graph.get("Leberberg");

            Station start = graph.get("Wallrissstrasse");
            Station end = graph.get("Columbusplatz");

            // Station start = graph.get("Hausfeldstrasse");
            // Station end = graph.get("Tesarekplatz");

            start.setCost(0);
            var stations = new ArrayList<>(graph.values());
            Collections.sort(stations);

            for (int i = 0; i < stations.size(); i++) {
                stations.get(i).setVisited(true);
                for (Neighbor neighbor : stations.get(i).getNeighbors()) {
                    if (!neighbor.getStation().isVisited()) {
                        int potential = stations.get(i).getCost() + neighbor.getCost();

                        if (stations.get(i).getPrevious() != null && stations.get(i).getPrevious().getLine() != neighbor.getLine()) {
                            potential += 5;
                        }

                        if (potential < neighbor.getStation().getCost()) {
                            neighbor.getStation().setCost(potential);
                            neighbor.getStation().setPrevious(neighborOfNeighborViaStation(neighbor, stations.get(i)));
                        }
                    }
                }

                Collections.sort(stations);
            }

            List<Neighbor> route = new ArrayList<>();
            route.add(new Neighbor(0, end, null));
            Neighbor temp = end.getPrevious();

            while (temp != null) {
                route.add(temp);
                temp = temp.getStation().getPrevious();
            }
            Collections.reverse(route);

            Line current = null;
            for (int i = 0; i < route.size(); i++) {
                var neighbor = route.get(i);
                if (neighbor.getStation().equals(start)) {
                    // List<Line> startLine = new ArrayList<>(start.getLines());
                    // startLine.retainAll(route.get(1).getFirst().getLines());
                    current = neighbor.getLine();
                    System.out.printf("--- line %s ---%n", neighbor.getLine().getName());
                    System.out.println("   " + neighbor.getStation().getName());
                } else {
                    System.out.printf("%02d %s%n", neighbor.getStation().getCost(), neighbor.getStation().getName());
                    if (neighbor.getLine() != null && neighbor.getLine() != current) {
                        current = neighbor.getLine();
                        System.out.printf("--- line change to %s ---%n", current.getName());
                        System.out.printf("%02d %s%n", neighbor.getStation().getCost() + 5, neighbor.getStation().getName());
                    }
                }
            }
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

    private static Map<String, Station> getGraph(String filePath) throws IOException {
        List<Line> lines = getLines(filePath);
        Map<String, Station> stations = new HashMap<>();

        for (Line line : lines) {
            for (Commute commute : line.getCommutes()) {
                Station s1 = stations.get(commute.getStation1());
                Station s2 = stations.get(commute.getStation2());

                if (s1 == null) {
                    s1 = new Station(commute.getStation1(), line);
                    stations.put(commute.getStation1(), s1);
                } else {
                    s1.getLines().add(line);
                }

                if (s2 == null) {
                    s2 = new Station(commute.getStation2(), line);
                    stations.put(commute.getStation2(), s2);
                } else {
                    s2.getLines().add(line);
                }

                s1.getNeighbors().add(new Neighbor(commute.getTime(), s2, line));
                s2.getNeighbors().add(new Neighbor(commute.getTime(), s1, line));
            }
        }

        return stations;
    }

    private static Neighbor neighborOfNeighborViaStation(Neighbor neighbor, Station station) {
        return neighbor.getStation().getNeighbors().stream().filter(n -> n.getStation() == station).findFirst().orElseThrow();
    }
}
