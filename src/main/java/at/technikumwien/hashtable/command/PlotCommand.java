package at.technikumwien.hashtable.command;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import at.technikumwien.hashtable.Hashtable;
import at.technikumwien.hashtable.History;
import at.technikumwien.hashtable.Stock;
import com.indvd00m.ascii.render.Region;
import com.indvd00m.ascii.render.Render;
import com.indvd00m.ascii.render.api.ICanvas;
import com.indvd00m.ascii.render.api.IContextBuilder;
import com.indvd00m.ascii.render.api.IRender;
import com.indvd00m.ascii.render.elements.Rectangle;
import com.indvd00m.ascii.render.elements.plot.Axis;
import com.indvd00m.ascii.render.elements.plot.AxisLabels;
import com.indvd00m.ascii.render.elements.plot.Plot;
import com.indvd00m.ascii.render.elements.plot.api.IPlotPoint;
import com.indvd00m.ascii.render.elements.plot.misc.PlotPoint;

public class PlotCommand implements Runnable {

    private static final int PLOT_WIDTH = 80;
    private static final int PLOT_HEIGHT = 20;
    private static final double PLOT_DETAIL = 0.75;

    private final Hashtable<String, String> abbrHashtable;
    private final Hashtable<String, Stock> stockHashtable;
    private final Scanner scanner;

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    public PlotCommand(Hashtable<String, String> abbrHashtable, Hashtable<String, Stock> stockHashtable, Scanner scanner) {
        this.abbrHashtable = abbrHashtable;
        this.stockHashtable = stockHashtable;
        this.scanner = scanner;
    }

    @Override
    public void run() {
        System.out.println("Name/Abbreviation:");
        String key = scanner.nextLine();

        Stock stock;
        String abbr = abbrHashtable.get(key);
        if (abbr == null) {
            stock = stockHashtable.get(key);
        } else {
            stock = stockHashtable.get(abbr);
        }

        if (stock == null) {
            System.err.println("No such stock");
        } else {
            printDiagram(stock);
        }
    }

    private void printDiagram(Stock stock) {
        List<IPlotPoint> points = new ArrayList<>();
        List<History> reversed = new ArrayList<>(stock.getHistories());
        Collections.reverse(reversed);

        LocalDate lastDay = reversed.get(reversed.size() - 1).getDate();
        long days = reversed.get(0).getDate().until(lastDay, ChronoUnit.DAYS);
        for (int i = 0; i < reversed.size(); i++) {
            LocalDate date = reversed.get(i).getDate();
            long daysToLastDay = date.until(lastDay, ChronoUnit.DAYS);
            points.add(new PlotPoint(days - daysToLastDay, reversed.get(i).getAdjustedClose()));
            if (i < reversed.size() - 1) {
                long daysBetween = date.until(reversed.get(i + 1).getDate(), ChronoUnit.DAYS);
                double incrementalDelta = (reversed.get(i + 1).getAdjustedClose() - reversed.get(i).getAdjustedClose()) / (daysBetween / PLOT_DETAIL);
                for (double j = PLOT_DETAIL; j < daysBetween; j += PLOT_DETAIL) {
                    points.add(new PlotPoint(days - daysToLastDay + j, reversed.get(i).getAdjustedClose() + j * incrementalDelta));
                }
            }
        }

		IRender render = new Render();
		IContextBuilder builder = render.newBuilder();
		builder.width(PLOT_WIDTH).height(PLOT_HEIGHT);
		builder.element(new Rectangle(0, 0, PLOT_WIDTH, PLOT_HEIGHT));
		builder.layer(new Region(1, 1, PLOT_WIDTH - 2, PLOT_HEIGHT - 2));
		builder.element(new Axis(points, new Region(0, 0, PLOT_WIDTH - 2, PLOT_HEIGHT - 2)));
		builder.element(new AxisLabels(points, new Region(0, 0, PLOT_WIDTH - 2, PLOT_HEIGHT - 2)));
		builder.element(new Plot(points, new Region(0, 0, PLOT_WIDTH - 2, PLOT_HEIGHT - 2)));
		ICanvas canvas = render.render(builder.build());
		String s = canvas.getText();
		System.out.println(s);
    }
}
