package com.ujazdowski.client.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ujazdowski.client.R;
import com.ujazdowski.client.service.dto.ExtraOfferDto;
import dagger.android.support.DaggerFragment;

public class OfferDetailsUpdateFragment extends DaggerFragment implements Title {
    @BindView(R.id.activity_offer_update)
    Button updateButton;
    @BindView(R.id.activity_offer_editmode)
    Switch editmode;
    private OfferFragment fragmentOffer;
    private Boolean editable = true;

    public static OfferDetailsUpdateFragment newInstance(ExtraOfferDto extraOfferDto) {
        OfferDetailsUpdateFragment fragment = new OfferDetailsUpdateFragment();
        Bundle args = new Bundle();
        args.putSerializable(ExtraOfferDto.class.getSimpleName(), extraOfferDto);
        fragment.setArguments(args);
        return fragment;
    }

    public void setEditable(Boolean editable) {
        // TODO remove
        editable = false;
        this.editable = editable;
        if (editmode != updateButton) {
            editmode.setEnabled(editable);
            updateButton.setEnabled(editable);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offer_details_update, container, false);
        ButterKnife.bind(this, view);
        editmode.setChecked(false);
        editmode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            fragmentOffer.setMode(isChecked);
            updateButton.setEnabled(isChecked);
        });
        updateButton.setOnClickListener(v -> {
            // TODO implementation
        });

        // TODO remove
        updateButton.setVisibility(View.GONE);
        editmode.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fragmentOffer = OfferFragment.newInstance((ExtraOfferDto) getArguments().getSerializable(ExtraOfferDto.class.getSimpleName()), editmode.isChecked());
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_offer_editable, fragmentOffer).commit();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public int getTitleResource() {
        return R.string.offer_details_page_title;
    }
}
