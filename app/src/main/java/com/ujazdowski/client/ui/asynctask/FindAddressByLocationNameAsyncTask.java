package com.ujazdowski.client.ui.asynctask;

import com.ujazdowski.client.domain.Address;
import java8.util.function.Consumer;

import java.util.ArrayList;
import java.util.List;

public class FindAddressByLocationNameAsyncTask extends AbstractFindLocationAsyncTask<String, Void, List<Address>> {
    public FindAddressByLocationNameAsyncTask(Consumer<List<Address>> consumer) {
        super(consumer);
    }

    @Override
    protected List<Address> doInBackground(String... locations) {
        List<Address> result = new ArrayList<>();
        for (String location: locations) {
            result.add(translate(location));
        }
        return result;
    }
}
