package com.ujazdowski.client.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.ujazdowski.client.R;
import com.ujazdowski.client.service.dto.ChatDto;
import com.ujazdowski.client.service.dto.CoordinatesDto;
import com.ujazdowski.client.service.dto.MessageDto;
import com.ujazdowski.client.ui.ItemType;
import com.ujazdowski.client.ui.activity.MapActivity;
import com.ujazdowski.client.ui.arguments.MapArguments;
import com.ujazdowski.client.ui.asynctask.FindAddressAsyncTask;
import com.ujazdowski.client.ui.viewholder.MessageViewHolder;
import com.ujazdowski.client.utils.DateUtils;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private ChatDto chat;

    public MessageAdapter(Context context) {
        this.context = context;
    }

    public void setChat(ChatDto chat) {
        this.chat = chat;
    }

    public void addMessage(MessageDto message) {
        chat.getMessages().add(message);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (ItemType.forValue(i)) {
            case LOCATION:
                return new MessageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message, viewGroup, false));
            case MESSAGE:
                return new ProposedPlaceViewHolder(LayoutInflater.from(context).inflate(R.layout.item_proposed_place, viewGroup, false));
        }
        throw new RuntimeException("Situation not Possible!");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder vh, int i) {
        if (i > 0) {
            MessageViewHolder messageAdapterViewHolder = (MessageViewHolder) vh;
            MessageDto message = this.chat.getMessages().get(i - 1);
            messageAdapterViewHolder.setAuthor(message.getEmail());
            messageAdapterViewHolder.setContent(message.getMessage());
            messageAdapterViewHolder.setDate(DateUtils.printableDateTimeFormat(message.getDate()));
        } else {
            ProposedPlaceViewHolder proposedPlaceViewHolder = (ProposedPlaceViewHolder) vh;
            proposedPlaceViewHolder.showOnMap.setOnClickListener(v -> {
                new FindAddressAsyncTask(addresses -> {
                    Intent intent = new Intent(context, MapActivity.class);
                    intent.putExtra(MapArguments.class.getSimpleName(), new MapArguments(chat.getSuggested_longitude(),
                            chat.getSuggested_latitude(), addresses.get(0).getLocation()));
                    context.startActivity(intent);
                }).execute(new CoordinatesDto(chat.getSuggested_latitude(), chat.getSuggested_longitude()));
            });
        }
    }

    @Override
    public int getItemCount() {
        return chat == null ? 0 : chat.getMessages().size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position > 0 ? ItemType.LOCATION.getType() : ItemType.MESSAGE.getType();
    }

    private class ProposedPlaceViewHolder extends RecyclerView.ViewHolder {
        private Button showOnMap;

        public ProposedPlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            showOnMap = itemView.findViewById(R.id.item_proposed_place_show_on_map_button);
        }
    }
}
