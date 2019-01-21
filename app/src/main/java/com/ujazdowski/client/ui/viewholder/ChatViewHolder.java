package com.ujazdowski.client.ui.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ujazdowski.client.R;

public class ChatViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.countOfUsersText)
    TextView countOfUsersText;
    @BindView(R.id.placeOfMeeting)
    TextView placeOfMeeting;
    @BindView(R.id.button_show_map)
    Button showMap;
    @BindView(R.id.accept_button)
    Button acceptButton;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public String getCountOfUsersText() {
        return countOfUsersText.getText().toString();
    }

    public void setCountOfUsersText(String countOfUsersText) {
        this.countOfUsersText.setText(countOfUsersText);
    }

    public String getPlaceOfMeeting() {
        return placeOfMeeting.getText().toString();
    }

    public void setPlaceOfMeeting(String placeOfMeeting) {
        this.placeOfMeeting.setText(placeOfMeeting);
    }

    public void setShowMap(View.OnClickListener onClickListener) {
        this.showMap.setOnClickListener(onClickListener);
    }

    public void setAcceptButton(View.OnClickListener onClickListener) {
        acceptButton.setOnClickListener(onClickListener);
    }
}