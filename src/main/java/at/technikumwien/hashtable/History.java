package at.technikumwien.hashtable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class History implements Comparable<History> {

    private final LocalDate date;
    private final double open;
    private final double max;
    private final double min;
    private final double close;
    private final double adjustedClose;
    private final long volume;

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    public History(LocalDate date, double open, double max, double min, double close, double adjustedClose, long volume) {
        this.date = date;
        this.open = open;
        this.max = max;
        this.min = min;
        this.close = close;
        this.adjustedClose = adjustedClose;
        this.volume = volume;
    }

    // //////////////////////////////////////////////////////////////////////////
    // Methoden
    // //////////////////////////////////////////////////////////////////////////

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof History)) {
            return false;
        }

        return date.equals(((History) obj).date);
    }

    @Override
    public String toString() {
        return "date: " + date.format(DateTimeFormatter.ISO_LOCAL_DATE) + " | open: " + open + 
                " | max: " + max + " | min: " + min + " | close: " + close + 
                " | adjusted close: " + adjustedClose + " | volume: " + volume;
    }

    @Override
    public int compareTo(History o) {
        return o.date.compareTo(date);
    }

    // //////////////////////////////////////////////////////////////////////////
    // Getter
    // //////////////////////////////////////////////////////////////////////////

    public LocalDate getDate() {
        return date;
    }

    public double getOpen() {
        return open;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public double getClose() {
        return close;
    }

    public double getAdjustedClose() {
        return adjustedClose;
    }

    public long getVolume() {
        return volume;
    }
}
