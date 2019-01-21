package com.ujazdowski.client.api;

import com.ujazdowski.client.service.dto.OfferDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface OfferApi {
    @POST("user-offers")
    Call<OfferDto> createOffer(@Body OfferDto request);

    @GET("user-offers")
    Call<List<OfferDto>> getCurrentUserOffers();
}
