package at.technikumwien.hashtable;

import java.time.LocalDate;

public class History {

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
