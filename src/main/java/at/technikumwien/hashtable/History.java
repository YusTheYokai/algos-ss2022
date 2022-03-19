package at.technikumwien.hashtable;

import java.time.LocalDate;

public record History(LocalDate date, double open, double max, double min, double close, double adjustedClose, long volume) implements Comparable<History> {

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
    public int compareTo(History o) {
        return o.date.compareTo(date);
    }
}
