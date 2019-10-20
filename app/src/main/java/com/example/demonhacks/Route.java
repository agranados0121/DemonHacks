package com.example.demonhacks;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Stores all Trains for a specific Route
 *
 * A Route is a collection of trains that share the same line, stationId, and destination
 */
public class Route implements Serializable {
    private String line;
    private String stationId;
    private String stationName;
    private String destination;
    private ArrayList<Train> trains;

    public Route (String line, String stationId, String stationName, String destination, ArrayList<Train> trains) {
        this.line = line;
        this.stationId = stationId;
        this.stationName = stationName;
        this.destination = destination;
        this.trains = trains;
    }

    public String getLine() {
        return line;
    }

    public String getStationId() {
        return stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public String getDestination() {
        return destination;
    }

    public ArrayList<Train> getTrains() {
        return trains;
    }

    public boolean sharesRoute(Route route) {
        if (this.line.equals(route.getLine()) && this.stationId.equals(route.getStationId()) && this.destination.equals(route.getDestination())) {
            return true;
        }
        return false;
    }
}