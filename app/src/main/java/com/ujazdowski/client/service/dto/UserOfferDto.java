package com.ujazdowski.client.service.dto;

import java.io.Serializable;


public class UserOfferDto implements Serializable {
    private Double longitude;

    private Double latitude;

    private Double distance;

    private Integer countOfItems;

    private Integer countOfItemsToGetBonus;

    private String expirationDate;

    public UserOfferDto(Double longitude, Double latitude, Double distance, Integer countOfItems, Integer countOfItemsToGetBonus, String expirationDate) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance = distance;
        this.countOfItems = countOfItems;
        this.countOfItemsToGetBonus = countOfItemsToGetBonus;
        this.expirationDate = expirationDate;
    }

    public UserOfferDto() {
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getCountOfItems() {
        return countOfItems;
    }

    public void setCountOfItems(Integer countOfItems) {
        this.countOfItems = countOfItems;
    }

    public Integer getCountOfItemsToGetBonus() {
        return countOfItemsToGetBonus;
    }

    public void setCountOfItemsToGetBonus(Integer countOfItemsToGetBonus) {
        this.countOfItemsToGetBonus = countOfItemsToGetBonus;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
