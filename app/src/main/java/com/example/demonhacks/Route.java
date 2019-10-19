package com.example.demonhacks;

import java.util.ArrayList;

/**
 * Stores all Trains for a specific Route
 *
 * A Route is a collection of trains that share the same line, station, and destination
 */
public class Route {
    private String line;
    private String station;
    private String destination;
    private ArrayList<Train> trains;

    public Route (String line, String station, String destination, ArrayList<Train> trains) {
        this.line = line;
        this.station = station;
        this.destination = destination;
        this.trains = trains;
    }

    public String getLine() {
        return line;
    }

    public String getStation() {
        return station;
    }

    public String getDestination() {
        return destination;
    }

    public ArrayList<Train> getTrains() {
        return trains;
    }

    public boolean sharesRoute(Route route) {
        if (line.equals(route.getLine()) &&
                station.equals(route.getStation()) &&
                destination.equals(route.getDestination())) {
            return true;
        }
        return false;
    }
}