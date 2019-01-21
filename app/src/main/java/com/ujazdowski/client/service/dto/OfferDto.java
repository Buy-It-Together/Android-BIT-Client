package com.ujazdowski.client.service.dto;

import java.io.Serializable;
import java.util.Date;

public class OfferDto implements Serializable {
    private Long id;
    private String link;
    private String longitude;
    private String latitude;
    private Double distance;
    private Long countOfItems;
    private Long countOfItemsToGetBonus;
    private Date expirationDate;


    public OfferDto() {
    }

    public OfferDto(Long id, String link, String longitude, String latitude, Double distance, Long countOfItems, Long countOfItemsToGetBonus, Date expirationDate) {
        this.id = id;
        this.link = link;
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance = distance;
        this.countOfItems = countOfItems;
        this.countOfItemsToGetBonus = countOfItemsToGetBonus;
        this.expirationDate = expirationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Long getCountOfItems() {
        return countOfItems;
    }

    public void setCountOfItems(Long countOfItems) {
        this.countOfItems = countOfItems;
    }

    public Long getCountOfItemsToGetBonus() {
        return countOfItemsToGetBonus;
    }

    public void setCountOfItemsToGetBonus(Long countOfItemsToGetBonus) {
        this.countOfItemsToGetBonus = countOfItemsToGetBonus;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
