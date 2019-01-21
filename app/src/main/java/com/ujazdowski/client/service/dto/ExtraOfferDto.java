package com.ujazdowski.client.service.dto;

import java.io.Serializable;
import java.util.Date;

public class ExtraOfferDto extends OfferDto implements Serializable {
    private String location;

    public ExtraOfferDto(String location) {
        this.location = location;
    }

    public ExtraOfferDto(Long id, String link, String longitude, String latitude, Double distance, Long countOfItems, Long countOfItemsToGetBonus, Date expirationDate, String location) {
        super(id, link, longitude, latitude, distance, countOfItems, countOfItemsToGetBonus, expirationDate);
        this.location = location;
    }

    public ExtraOfferDto() {
    }

    public ExtraOfferDto(OfferDto offerDto) {
        this(offerDto.getId(), offerDto.getLink(), offerDto.getLongitude(), offerDto.getLatitude(), offerDto.getDistance(), offerDto.getCountOfItems(),
                offerDto.getCountOfItemsToGetBonus(), offerDto.getExpirationDate(), null);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ExtraOfferDto location(String location) {
        this.setLocation(location);
        return this;
    }
}
