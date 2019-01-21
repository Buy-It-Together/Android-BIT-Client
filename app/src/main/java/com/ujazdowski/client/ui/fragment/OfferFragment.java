package com.ujazdowski.client.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ujazdowski.client.R;
import com.ujazdowski.client.ui.validator.Validator;
import com.ujazdowski.client.service.dto.ExtraOfferDto;
import dagger.android.support.DaggerFragment;
import java8.util.Optional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class OfferFragment extends DaggerFragment {
    private final static String EDITABLE_MODE_ARG = "EDITABLE_MODE_ARGS";

    @BindView(R.id.locationTextInputId)
    TextInputEditText locationInput;
    @BindView(R.id.linkTextInputId)
    TextInputEditText linkInput;
    @BindView(R.id.distanceInput)
    TextInputEditText distance;
    @BindView(R.id.numOfItemsForYouInput)
    TextInputEditText numOfItemsForUInput;
    @BindView(R.id.numOfItemsToGetBonusInput)
    TextInputEditText numOfItemsToGetBonusInput;
    @BindView(R.id.calendarView)
    CalendarView calendarView;
    private List<View> editableViews = new ArrayList<>();

    public static OfferFragment newInstance(ExtraOfferDto extraOfferDto, boolean editable) {
        OfferFragment fragment = new OfferFragment();
        Bundle args = new Bundle();
        args.putSerializable(ExtraOfferDto.class.getSimpleName(), extraOfferDto);
        args.putBoolean(EDITABLE_MODE_ARG, editable);
        fragment.setArguments(args);
        return fragment;
    }

    private void initializeEditableViewsList() {
        editableViews.addAll(Arrays.asList(linkInput, locationInput, distance, numOfItemsForUInput,
                numOfItemsToGetBonusInput, calendarView));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_offer, container, false);
        ButterKnife.bind(this, view);
        calendarView.setOnDateChangeListener((view1, year, month, day) ->
                view1.setDate(new Date(year - 1900, month, day).getTime()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setData((ExtraOfferDto) getArguments().getSerializable(ExtraOfferDto.class.getSimpleName()));
        setMode(getArguments().getBoolean(EDITABLE_MODE_ARG));
        super.onViewCreated(view, savedInstanceState);
    }

    public void setMode(Boolean editable) {
        if (editableViews.isEmpty()) {
            initializeEditableViewsList();
        }
        for (View v : editableViews) {
            v.setEnabled(editable);
        }
    }

    public void setData(ExtraOfferDto offerDto) {
        this.linkInput.setText(Optional.ofNullable(offerDto.getLink()).orElse(new String()));
        this.locationInput.setText(Optional.ofNullable(offerDto.getLocation()).orElse(new String()));
        this.distance.setText(String.valueOf(Optional.ofNullable(offerDto.getDistance()).orElse(2.5)));
        this.numOfItemsForUInput.setText(String.valueOf(Optional.ofNullable(offerDto.getCountOfItems()).orElse(1L)));
        this.numOfItemsToGetBonusInput.setText(String.valueOf(Optional.ofNullable(offerDto.getCountOfItemsToGetBonus()).orElse(10L)));
        this.calendarView.setDate((offerDto.getExpirationDate() != null ? offerDto.getExpirationDate() : new Date()).getTime());
    }

    public int validate() {
        if (linkInput.getText().toString().isEmpty()) {
            return R.string.error_offer_link;
        }

        if (locationInput.getText().toString().isEmpty()) {
            return R.string.error_offer_city;
        }

        if (!Validator.isDouble(distance.getText().toString())) {
            return R.string.error_offer_distance;
        }

        if (!Validator.isLong(numOfItemsForUInput.getText().toString())) {
            return R.string.error_offer_forU;
        }

        if (!Validator.isLong(numOfItemsToGetBonusInput.getText().toString())) {
            return R.string.error_offer_to_get_bonus;

        }

        if (!Validator.isAfterToday(new Date(calendarView.getDate()))) {
            return R.string.error_offer_to_delivery;
        }

        if (Long.parseLong(numOfItemsForUInput.getText().toString()) > Long.parseLong(numOfItemsToGetBonusInput.getText().toString())) {
            return R.string.error_offer_forU_gt_bonus;
        }

        return 0;
    }

    public String getLink() {
        return linkInput.getText().toString();
    }

    public Double getDistance() {
        return Double.parseDouble(distance.getText().toString());
    }

    public Long getNumOfItemsForU() {
        return Long.parseLong(numOfItemsForUInput.getText().toString());
    }

    public Long getNumOfItemsToGetBonus() {
        return Long.parseLong(numOfItemsToGetBonusInput.getText().toString());
    }

    public Date getDeliveryDate() {
        return new Date(calendarView.getDate());
    }

    public String getLocation() {
        return locationInput.getText().toString();
    }

}
