package com.ujazdowski.client.service.geocoder;

import com.ujazdowski.client.domain.Address;
import com.ujazdowski.client.repository.AddressRepository;

import javax.inject.Inject;

public abstract class GeocoderAbstractService {

    public GeocoderAbstractService(){
    }

    private AddressRepository addressRepository = new AddressRepository();

    public Address translate(String latitude, String longitude) {
        Address address = addressRepository.find(latitude, longitude);
        if (address != null) {
            return address;
        }
        address = findAddressByCoordinates(latitude, longitude);
        address.setLatitude(latitude);
        address.setLongitude(longitude);
        addressRepository.create(address);
        return address;
    }

    public Address translate(String location) {
        Address address = addressRepository.find(location);
        if  (address != null){
            return address;
        }
        address = findCoordinatesByAddress(location);
        addressRepository.create(address);
        return address;
    }

    protected abstract Address findCoordinatesByAddress(String location);

    protected abstract Address findAddressByCoordinates(String latitude, String longitude);
}
