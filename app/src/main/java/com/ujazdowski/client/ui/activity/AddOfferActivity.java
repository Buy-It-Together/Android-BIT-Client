package com.ujazdowski.client.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ujazdowski.client.R;
import com.ujazdowski.client.ui.ResultsCodes;
import com.ujazdowski.client.ui.asynctask.FindAddressByLocationNameAsyncTask;
import com.ujazdowski.client.ui.fragment.OfferFragment;
import com.ujazdowski.client.domain.Address;
import com.ujazdowski.client.service.ErrorService;
import com.ujazdowski.client.service.OfferService;
import com.ujazdowski.client.service.dto.ExtraOfferDto;
import com.ujazdowski.client.service.dto.OfferDto;
import com.ujazdowski.client.utils.Progress;
import dagger.android.support.DaggerAppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;

public class AddOfferActivity extends DaggerAppCompatActivity {

    @Inject
    public OfferService offerService;

    @BindView(R.id.buttonAddOffer)
    Button addOfferButton;

    @BindView(R.id.add_offer_progress_bar)
    View progressBar;

    @BindView(R.id.add_offer_form)
    View addOfferForm;
    @Inject
    ErrorService errorService;
    private OfferFragment offerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);
        ButterKnife.bind(this);

        offerFragment = OfferFragment.newInstance(new ExtraOfferDto(), true);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_add_offer_fragment_container, offerFragment).commit();

        addOfferButton.setOnClickListener(v -> {
            int validate = offerFragment.validate();
            if (validate != 0) {
                toast(validate);
                return;
            }

            showProgress(true);

            new FindAddressByLocationNameAsyncTask(addresses -> {
                Address address = addresses.get(0);
                offerService.addOffer(new AddOfferCallBack(), offerFragment.getLink(), address,
                        offerFragment.getDistance(), offerFragment.getNumOfItemsForU(),
                        offerFragment.getNumOfItemsToGetBonus(), offerFragment.getDeliveryDate());

            }).execute(offerFragment.getLocation());
        });
    }

    private void toast(int message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void showProgress(final Boolean show) {
        Progress.showProgress(show, progressBar, addOfferForm, getResources());
    }

    private class AddOfferCallBack implements Callback<OfferDto> {

        @Override
        public void onResponse(Call<OfferDto> call, Response<OfferDto> response) {
            showProgress(false);
            if (response.isSuccessful()) {
                Intent data = new Intent();
                data.putExtra(OfferDto.class.getSimpleName(), response.body());
                setResult(ResultsCodes.SUCCESS.getStatusCode(), data);
                finish();
            } else {
                errorService.handle(AddOfferActivity.this, response);
                toast(R.string.error_offer_can_not_send_data);
            }
        }

        @Override
        public void onFailure(Call<OfferDto> call, Throwable t) {
            showProgress(false);
            Toast.makeText(getApplicationContext(), "Check your internet connection!", Toast.LENGTH_SHORT).show();
        }
    }
}
