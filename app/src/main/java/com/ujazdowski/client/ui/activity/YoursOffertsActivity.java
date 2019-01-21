package com.ujazdowski.client.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ujazdowski.client.R;
import com.ujazdowski.client.service.ErrorService;
import com.ujazdowski.client.service.OfferService;
import com.ujazdowski.client.service.dto.CoordinatesDto;
import com.ujazdowski.client.service.dto.ExtraOfferDto;
import com.ujazdowski.client.service.dto.OfferDto;
import com.ujazdowski.client.ui.ResultsCodes;
import com.ujazdowski.client.ui.adapter.OfferAdapter;
import com.ujazdowski.client.ui.asynctask.FindAddressAsyncTask;
import com.ujazdowski.client.ui.asynctask.FindLocationsAsyncTask;
import dagger.android.AndroidInjection;
import dagger.android.support.DaggerAppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class YoursOffertsActivity extends DaggerAppCompatActivity {
    public final static int ADD_USER_OFFER_REQUEST = 0xA2;
    private final List<ExtraOfferDto> offers = new ArrayList<>();
    @Inject
    OfferService offerService;
    @Inject
    ErrorService errorService;
    private OfferAdapter adapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton addOfferButton;
    @BindView(R.id.logout)
    FloatingActionButton logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yours_offerts);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new OfferAdapter(this, offers);
        recyclerView.setAdapter(adapter);
        initAdapter();

        addOfferButton.setOnClickListener(view -> startActivityForResult(new Intent(YoursOffertsActivity.this, AddOfferActivity.class), ADD_USER_OFFER_REQUEST));

        logoutButton.setOnClickListener(v -> {
            errorService.logout(YoursOffertsActivity.this);
        });
    }

    private void initAdapter() {
        offerService.getCurrentUserOffers(new CurrentUserOffersCallback());
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ADD_USER_OFFER_REQUEST && resultCode == ResultsCodes.SUCCESS.getStatusCode()) {
            ExtraOfferDto extraOfferDto = new ExtraOfferDto((OfferDto) data.getExtras().getSerializable(OfferDto.class.getSimpleName()));
            new FindAddressAsyncTask(addresses -> {
                extraOfferDto.setLatitude(addresses.get(0).getLatitude());
                extraOfferDto.setLongitude(addresses.get(0).getLongitude());
                extraOfferDto.setLocation(addresses.get(0).getLocation());
                if (offers.add(extraOfferDto)) {
                    adapter.notifyDataSetChanged();
                }
            }).execute(new CoordinatesDto(extraOfferDto.getLatitude(), extraOfferDto.getLongitude()));
        }
    }

    private class CurrentUserOffersCallback implements Callback<List<OfferDto>> {

        @Override
        public void onResponse(Call<List<OfferDto>> call, Response<List<OfferDto>> response) {
            if (response.isSuccessful()) {
                offers.clear();
                FindLocationsAsyncTask task = new FindLocationsAsyncTask(extraOfferDtos -> {
                    offers.addAll(extraOfferDtos);
                    adapter.notifyDataSetChanged();
                }, true);
                OfferDto[] o = new OfferDto[response.body().size()];
                response.body().toArray(o);
                task.execute(o);
            } else {
                errorService.handle(YoursOffertsActivity.this, response);
            }
        }

        @Override
        public void onFailure(Call<List<OfferDto>> call, Throwable t) {
            toast("something gone wrong!");
        }
    }
}
