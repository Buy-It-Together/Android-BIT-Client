package com.ujazdowski.client.service.geocoder;

import com.ujazdowski.client.domain.Address;
import java8.util.Optional;
import org.osmdroid.bonuspack.location.GeocoderNominatim;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

public class NominatimGeocoderService extends GeocoderAbstractService {
    private final static String EMPTY_STRING = new String();
    private final static String UNKNOWN_ADDRESS = "UNKNOWN_ADDRESS";
    private final static String USER_AGENT = "Mobile Bit It Together v0.4";
    private final static Integer MAX_RESULTS = 1;
    private final static GeocoderNominatim geocoder = new GeocoderNominatim(Locale.ENGLISH, USER_AGENT);
    private static Date lastRequest = new Date();

    @Inject
    public NominatimGeocoderService() {
        super();
    }

    @Override
    protected Address findCoordinatesByAddress(String location) {
        List<android.location.Address> addresses = null;
        try {
            waitBeforeRequest();
            addresses = geocoder.getFromLocationName(location, MAX_RESULTS);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return disassemblyResults(addresses);
    }

    @Override
    protected Address findAddressByCoordinates(String latitude, String longitude) {
        List<android.location.Address> addresses = null;
        try {
            waitBeforeRequest();
            addresses = geocoder.getFromLocation(Double.valueOf(latitude), Double.valueOf(longitude), MAX_RESULTS);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return disassemblyResults(addresses, latitude, longitude);
    }

    private Address disassemblyResults(List<android.location.Address> addresses) {
        return disassemblyResults(addresses, "0.0", "0.0");
    }

    private Address disassemblyResults(List<android.location.Address> addresses, String latitude, String longitude) {
        if (Optional.ofNullable(addresses).orElse(Collections.emptyList()).size() > 0) {
            return mapToCustomAddress(addresses.get(0));
        } else {
            return new Address(UNKNOWN_ADDRESS, latitude, longitude);
        }
    }

    private Address mapToCustomAddress(android.location.Address address) {
        Address custom = new Address();
        custom.setLatitude(String.valueOf(address.getLatitude()));
        custom.setLongitude(String.valueOf(address.getLongitude()));

        List<String> add = Arrays.asList(address.getCountryName(), address.getAdminArea(), address.getSubAdminArea(),
                address.getLocality(), address.getPostalCode(), address.getThoroughfare(), address.getSubThoroughfare());

        StringBuilder addressBuilder = new StringBuilder();

        for (String str : add) {
            if (!Optional.ofNullable(str).orElse(EMPTY_STRING).isEmpty()) {
                addressBuilder.append(str).append(' ');
            }
        }

        custom.setLocation(addressBuilder.toString().trim());
        return custom;
    }

    private void waitBeforeRequest() throws InterruptedException {
        Long millisToWait = millisToWait();
        if (millisToWait > 0) {
            synchronized (this) {
                wait(millisToWait);
                this.notify();
            }
        }
    }

    private Long millisToWait() {
        Date now = new Date();
        Long diff = now.getTime() - lastRequest.getTime();
        lastRequest = now;
        return diff > 1000 ? 0 : diff;
    }
}
