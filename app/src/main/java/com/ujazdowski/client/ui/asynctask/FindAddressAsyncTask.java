package com.ujazdowski.client.ui.asynctask;

import com.ujazdowski.client.domain.Address;
import com.ujazdowski.client.service.dto.CoordinatesDto;
import java8.util.function.Consumer;

import java.util.ArrayList;
import java.util.List;

public class FindAddressAsyncTask extends AbstractFindLocationAsyncTask<CoordinatesDto, Void, List<Address>> {
    public FindAddressAsyncTask(Consumer<List<Address>> consumer) {
        super(consumer);
    }

    @Override
    protected List<Address> doInBackground(CoordinatesDto... coordinatesDtos) {
        List<Address> result = new ArrayList<>();
        for (CoordinatesDto coordinatesDto : coordinatesDtos) {
            result.add(translate(coordinatesDto.getLatitude(), coordinatesDto.getLongitude()));
        }
        return result;
    }
}
