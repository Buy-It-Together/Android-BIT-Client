package com.ujazdowski.client.ui.asynctask;

import com.ujazdowski.client.domain.Address;
import com.ujazdowski.client.service.dto.ExtraOfferDto;
import com.ujazdowski.client.service.dto.OfferDto;
import java8.util.function.Consumer;

import java.util.ArrayList;
import java.util.List;

public class FindLocationsAsyncTask extends AbstractFindLocationAsyncTask<OfferDto, Void, List<ExtraOfferDto>> {
    private final boolean byCoordinates;

    public FindLocationsAsyncTask(Consumer<List<ExtraOfferDto>> consumer, boolean byCoordinates) {
        super(consumer);
        this.byCoordinates = byCoordinates;
    }

    @Override
    protected List<ExtraOfferDto> doInBackground(OfferDto... offerDtos) {
        List<ExtraOfferDto> results = new ArrayList<>();
        if (byCoordinates) {
            for (OfferDto offer : offerDtos) {
                results.add(new ExtraOfferDto(offer).location(translate(offer.getLatitude(), offer.getLongitude()).getLocation()));
            }
        } else {
            for (OfferDto offer : offerDtos) {
                if (!(offer instanceof ExtraOfferDto)) {
                    throw new ClassCastException();
                }
                ExtraOfferDto extraOfferDto = ((ExtraOfferDto) offer);
                Address translate = translate(extraOfferDto.getLocation());
                extraOfferDto.setLatitude(translate.getLatitude());
                extraOfferDto.setLongitude(translate.getLongitude());
                extraOfferDto.setLocation(translate.getLocation());
                results.add(extraOfferDto);
            }
        }
        return results;
    }
}
