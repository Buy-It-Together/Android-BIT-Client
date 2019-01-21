package com.ujazdowski.client.dagger;

import com.ujazdowski.client.ui.activity.*;
import com.ujazdowski.client.ui.fragment.ChatFragment;
import com.ujazdowski.client.ui.fragment.InvitationsFragment;
import com.ujazdowski.client.ui.fragment.OfferDetailsUpdateFragment;
import com.ujazdowski.client.ui.fragment.OfferFragment;
import com.ujazdowski.client.service.geocoder.GeocoderAbstractService;
import com.ujazdowski.client.service.geocoder.NominatimGeocoderService;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector
    abstract AddOfferActivity bindAddOfferActivity();

    @ContributesAndroidInjector
    abstract YoursOffertsActivity bindYoursOfferActivity();

    @ContributesAndroidInjector
    abstract OfferActivity bindOfferActivity();

    @ContributesAndroidInjector
    abstract MapActivity bindMapActivity();

    @ContributesAndroidInjector
    abstract ChatFragment bindChatFragment();

    @ContributesAndroidInjector
    abstract InvitationsFragment bindInvitationsFragment();

    @ContributesAndroidInjector
    abstract OfferDetailsUpdateFragment bindOfferDetailsUpdateFragment();

    @ContributesAndroidInjector
    abstract OfferFragment bindOfferFragment();

    @Provides
    static GeocoderAbstractService provideGeocoderService(NominatimGeocoderService geocoderService) {
        return geocoderService;
    }

}