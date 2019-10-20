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
    private String hexColor;

    public Route (String line, String stationId, String stationName, String destination, ArrayList<Train> trains) {

        if (line.equals("Brn")) hexColor = "#b38054";
        else if (line.equals("P")) hexColor = "#800080";
        else if (line.equals("Red")) hexColor = "#ed2b15";
        else if (line.equals("Blue")) hexColor = "#3E86F5";
        else if (line.equals("G")) hexColor = "#34B13C";
        else if (line.equals("Org")) hexColor = "#FFA200";
        else if (line.equals("Pink")) hexColor = "#F19CF5";
        else hexColor = "#F1EE17";

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

    public String getHexColor() {
        return hexColor;
    }

    public boolean sharesRoute(Route route) {
        if (this.line.equals(route.getLine()) && this.stationId.equals(route.getStationId()) && this.destination.equals(route.getDestination())) {
            return true;
        }
        return false;
    }
}