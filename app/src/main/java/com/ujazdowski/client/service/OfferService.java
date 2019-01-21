package com.ujazdowski.client.service;

import com.ujazdowski.client.domain.Address;
import com.ujazdowski.client.api.OfferApi;
import com.ujazdowski.client.service.dto.OfferDto;
import com.ujazdowski.client.utils.DateUtils;
import retrofit2.Callback;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

public class OfferService extends ApiService<OfferApi> {
    @Inject
    public OfferService() {
        super(OfferApi.class, true);
    }

    public void addOffer(Callback<OfferDto> callback, String link, Address address, Double distance,
                         Long itemsForClient, Long itemsToGetBonus, Date deliveryDate) {
        OfferDto offerDto = new OfferDto(null, link, address.getLongitude(), address.getLatitude(), distance,
                itemsForClient, itemsToGetBonus, DateUtils.toDate(DateUtils.toInstantFormat(deliveryDate)));
        getApi().createOffer(offerDto).enqueue(callback);
    }

    public void getCurrentUserOffers(Callback<List<OfferDto>> callback) {
        getApi().getCurrentUserOffers().enqueue(callback);
    }

    @Override
    public OfferApi getApi() {
        return super.api;
    }
}
