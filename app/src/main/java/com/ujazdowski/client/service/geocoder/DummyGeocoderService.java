package com.ujazdowski.client.service.geocoder;

import com.ujazdowski.client.domain.Address;

import javax.inject.Inject;

public class DummyGeocoderService extends GeocoderAbstractService {

    private static Address dummyAddress = new Address("DummyLocation", "0.0", "0.0");

    @Inject
    public DummyGeocoderService() {
        super();
    }

    @Override
    protected Address findCoordinatesByAddress(String location) {
        return dummyAddress;
    }

    @Override
    protected Address findAddressByCoordinates(String latitude, String longitude) {
        return dummyAddress;
    }
}
