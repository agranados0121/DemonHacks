package com.example.demonhacks;

import java.util.Comparator;

public class SortByLine implements Comparator<Route> {
    public int compare(Route r1, Route r2) {
        return r1.getLine().compareTo(r2.getLine());
    }
}