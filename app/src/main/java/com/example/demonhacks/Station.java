package com.example.demonhacks;

import java.io.Serializable;

public class Station implements Serializable {

    private String mapId;
    private String name;
    private String lat;
    private String lon;

    public Station(String mapId, String name, String lat, String lon) {
        this.mapId = mapId;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public String getMapId() {
        return mapId;
    }

    public String getName() {
        return name;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}
