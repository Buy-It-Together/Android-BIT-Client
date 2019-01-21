package com.ujazdowski.client.ui.asynctask;

import android.os.AsyncTask;
import com.ujazdowski.client.domain.Address;
import com.ujazdowski.client.service.geocoder.GeocoderAbstractService;
import com.ujazdowski.client.service.geocoder.NominatimGeocoderService;
import java8.util.function.Consumer;

public abstract class AbstractFindLocationAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    private final Consumer<Result> consumer;
    private final static GeocoderAbstractService mapService = new NominatimGeocoderService();

    protected AbstractFindLocationAsyncTask(Consumer<Result> consumer) {
        this.consumer = consumer;
    }

    protected Address translate(String location) {
        return mapService.translate(location);
    }

    protected Address translate(String latitude, String longitiude) {
        return mapService.translate(latitude, longitiude);
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        consumer.accept(result);
    }
}
