package com.ujazdowski.client.service.dto;

import java.io.Serializable;

public class CoordinatesDto implements Serializable {
    private String latitude;

    private String longitude;

    public CoordinatesDto(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public CoordinatesDto() {
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
