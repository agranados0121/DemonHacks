package com.example.demonhacks;

import java.util.Comparator;

public class SortByLine implements Comparator<Route> {

    // TODO: Seeks to work but test
    public int compare(Route r1, Route r2) {
        int cmp = r1.getStationId().compareTo(r2.getStationId());
        if (cmp == 0) {
            return r1.getLine().compareTo(r2.getLine());
        }
        return cmp;
    }
}