package com.ujazdowski.client.ui.arguments;

import java.io.Serializable;

public class MapArguments implements Serializable {
    private String longitude;
    private String latitude;
    private String location;

    public MapArguments(String longitude, String latitude, String location) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.location = location;
    }

    public MapArguments() {
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
