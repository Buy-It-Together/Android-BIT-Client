package com.ujazdowski.client.ui.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ujazdowski.client.R;

public class OfferViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.offert_link)
    TextView link;
    @BindView(R.id.offer_expiried_date)
    TextView expiredDate;
    @BindView(R.id.item_offer_id)
    LinearLayout itemLayout;

    public OfferViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public TextView getLink() {
        return link;
    }

    public void setLink(TextView link) {
        this.link = link;
    }

    public TextView getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(TextView expiredDate) {
        this.expiredDate = expiredDate;
    }

    public LinearLayout getItemLayout() {
        return itemLayout;
    }

    public void setItemLayout(LinearLayout itemLayout) {
        this.itemLayout = itemLayout;
    }
}
