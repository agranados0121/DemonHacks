package com.example.demonhacks;

import java.io.Serializable;

public class Train implements Serializable {

    private String arrivalTime;
    private String timeRemaining;
    private String latitude;
    private String longitude;

    public Train(String arrivalTime, String timeRemaining, String latitude, String longitude) {
        this.arrivalTime = arrivalTime;
        this.timeRemaining = timeRemaining;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getTimeRemaining() {
        return timeRemaining;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}