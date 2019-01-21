package com.ujazdowski.client.repository;

import com.ujazdowski.client.domain.Address;
import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * Repozytorium zarządzające adresami
 */
public class AddressRepository {

    private final static String LATITUDE = "latitude";
    private final static String LONGITUDE = "longitude";
    private final static String LOCATION = "location";

    public final Address create(Address address) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final Address copy = realm.copyToRealm(address);
        realm.commitTransaction();
        return copy;
    }

    private Address copyFromRealm(Address address) {
        return address != null ? getInstance().copyFromRealm(address) : null;
    }

    public Address find(String latitude, String longitude) {
        return copyFromRealm(query().equalTo(LATITUDE, latitude).and().equalTo(LONGITUDE, longitude).findFirst());
    }

    public Address find(String location) {
        return copyFromRealm(query().equalTo(LOCATION, location).findFirst());
    }

    private Realm getInstance() {
        return Realm.getDefaultInstance();
    }

    private RealmQuery<Address> query() {
        return getInstance().where(Address.class);
    }
}
