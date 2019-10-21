package com.example.demonhacks;

import androidx.annotation.Nullable;

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

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof Station)) {
            return false;
        }
        Station station = (Station) obj;
        return  station.mapId.equals(mapId) &&
                station.name.equals(name);
    }
}
