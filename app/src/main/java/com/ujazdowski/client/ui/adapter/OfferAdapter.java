package com.ujazdowski.client.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ujazdowski.client.R;
import com.ujazdowski.client.service.dto.ExtraOfferDto;
import com.ujazdowski.client.ui.activity.OfferActivity;
import com.ujazdowski.client.ui.viewholder.OfferViewHolder;
import com.ujazdowski.client.utils.DateUtils;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferViewHolder> {
    private Context context;
    private List<ExtraOfferDto> offerDtos;

    public OfferAdapter(Context context, List<ExtraOfferDto> offerDtos) {
        this.context = context;
        this.offerDtos = offerDtos;
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_offert, viewGroup, false);
        return new OfferViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder viewHolder, final int i) {
        viewHolder.getExpiredDate().setText(DateUtils.printableFormat(offerDtos.get(i).getExpirationDate()));
        viewHolder.getLink().setText(offerDtos.get(i).getLink());
        viewHolder.getItemLayout().setOnClickListener(v -> {
            ExtraOfferDto offerDto = offerDtos.get(i);
            Intent intent = new Intent(context, OfferActivity.class);
            intent.putExtra(ExtraOfferDto.class.getSimpleName(), offerDto);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return offerDtos.size();
    }
}
