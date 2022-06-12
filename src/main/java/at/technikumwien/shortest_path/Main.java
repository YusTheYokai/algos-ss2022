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

    private static final String STATION_REGEX = "[a-zA-Z0-9\\.\\-\\/, ]+";

    public static void main(String[] args) {
        try {
            List<Line> lines = getLines(args[0]);

            Map<String, List<Station>> graph = getGraph(lines);
            List<Station> start = graph.get(args[1]);
            List<Station> end = graph.get(args[2]);

            start.forEach(s -> s.setCost(0));

            List<Station> stations = new ArrayList<>(graph.values().stream().flatMap(List::stream).toList());
            Collections.sort(stations);

            dijkstra(stations);

            buildAndPrintRoute(end);
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

    private static Map<String, List<Station>> getGraph(List<Line> lines) {
        Map<String, List<Station>> stations = new HashMap<>();

        for (Line line : lines) {
            for (Commute commute : line.getCommutes()) {
                Station s1 = addStation(stations, commute.getStation1(), line);
                Station s2 = addStation(stations, commute.getStation2(), line);

                s1.getNeighbors().add(new Neighbor(commute.getTime(), s2));
                s2.getNeighbors().add(new Neighbor(commute.getTime(), s1));
            }
        }

        return stations;
    }

    private static Station addStation(Map<String, List<Station>> stations, String name, Line line) {
        Station station;

        // existiert die Station bereits, unabhängig von der Linie?
        if (stations.containsKey(name)) {
            List<Station> list = stations.get(name);

            // existiert die Station auf der selben Linie?
            if (list.stream().anyMatch(s -> s.getLine() == line)) {
                // wenn ja wird sie weiterverwendet
                station = list.stream().filter(s -> s.getLine() == line).findFirst().orElseThrow();
            } else {
                // wenn nicht wird eine neue Station erstellt
                // und die Nachbarn der Station hinzugefügt
                station = new Station(name, line);
                list.add(station);
                list.forEach(s -> {
                    s.addNeighbor(new Neighbor(5, station));
                    station.addNeighbor(new Neighbor(5, s));
                });
            }
        } else {
            // wenn nicht wird eine neue Station erstellt
            List<Station> list = new ArrayList<>();
            station = new Station(name, line);
            list.add(station);
            stations.put(name, list);
        }

        return station;
    }

    private static void dijkstra(List<Station> stations) {
        while (!stations.isEmpty()) {
            Station station = stations.get(0);
            stations.remove(0);

            for (Neighbor neighbor : station.getNeighbors()) {
                if (stations.contains(neighbor.getStation())) {
                    int cost = station.getCost() + neighbor.getCost();

                    if (neighbor.getStation().getCost() > cost) {
                        neighbor.getStation().setCost(cost);
                        neighbor.getStation().setPrevious(station);
                    }
                }
            }

            Collections.sort(stations);
        }
    }

    private static void buildAndPrintRoute(List<Station> end) {
        Collections.sort(end);
        Station destination = end.get(0);
        List<Station> route = new ArrayList<>();

        while (destination != null) {
            route.add(0, destination);
            destination = destination.getPrevious();
        }

        route.forEach(s -> {
            if (s.getPrevious() != null && s.getPrevious().getLine() != s.getLine()) {
                System.out.printf("+5 | --- | Umstieg von %s zu %s%n", s.getPrevious().getLine(), s.getLine()); // NOSONAR
            }
            System.out.printf("%02d | %s | %s%n", s.getCost(), formatLine(s.getLine()), s); // NOSONAR
        });
    }

    private static String formatLine(Line line) {
        StringBuilder sb = new StringBuilder(line.getName());
        while (sb.length() < 3) {
            sb.insert(0, " ");
        }
        return sb.toString();
    }
}
