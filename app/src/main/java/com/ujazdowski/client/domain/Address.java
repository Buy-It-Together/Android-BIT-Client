package com.ujazdowski.client.domain;

import io.realm.RealmObject;

import java.io.Serializable;

/**
 * Model prezentujący adres wraz z współrzędnymi geograficznymi
 */
public class Address extends RealmObject implements Serializable {

    private String location;

    private String latitude;

    private String longitude;

    public Address(String location, String latitude, String longitude) {
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Address() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
