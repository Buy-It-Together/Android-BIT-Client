package com.ujazdowski.client.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.ujazdowski.client.R;
import com.ujazdowski.client.service.ChatService;
import com.ujazdowski.client.service.ErrorService;
import com.ujazdowski.client.service.dto.ChatDto;
import com.ujazdowski.client.service.dto.CoordinatesDto;
import com.ujazdowski.client.service.dto.UserOfferChatDto;
import com.ujazdowski.client.ui.activity.MapActivity;
import com.ujazdowski.client.ui.arguments.MapArguments;
import com.ujazdowski.client.ui.asynctask.FindAddressAsyncTask;
import com.ujazdowski.client.ui.fragment.InvitationsFragment;
import com.ujazdowski.client.ui.viewholder.ChatViewHolder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Collections;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    private ChatService chatService;
    private ErrorService errorService;

    private Activity context;
    private List<ChatDto> chatDtos;

    public ChatAdapter(ChatService chatService, ErrorService errorService, Activity context, List<ChatDto> chatDtos) {
        this.chatService = chatService;
        this.errorService = errorService;
        this.context = context;
        this.chatDtos = chatDtos;

    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_chat, viewGroup, false);
        return new ChatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder viewHolder, int i) {
        ChatDto chat = chatDtos.get(i);
        CoordinatesDto[] coordinatesDtos = new CoordinatesDto[1];
        Collections.singletonList(new CoordinatesDto(chat.getSuggested_latitude(), chat.getSuggested_longitude())).toArray(coordinatesDtos);
        new FindAddressAsyncTask(addresses -> {
            viewHolder.setPlaceOfMeeting(addresses.get(0).getLocation());
//                notifyDataSetChanged();
        }).execute(coordinatesDtos);

        viewHolder.setCountOfUsersText(String.valueOf(chat.getUserOfferChatVMs().size()));
        viewHolder.setShowMap(v -> new FindAddressAsyncTask(addresses -> {
            Intent intent = new Intent(context, MapActivity.class);
            intent.putExtra(MapArguments.class.getSimpleName(), new MapArguments(chat.getSuggested_longitude(),
                    chat.getSuggested_latitude(), addresses.get(0).getLocation()));
            context.startActivity(intent);
        }).execute(new CoordinatesDto(chat.getSuggested_latitude(), chat.getSuggested_longitude())));

        viewHolder.setAcceptButton(v -> {
            chatService.accept(new Callback<UserOfferChatDto>() {
                @Override
                public void onResponse(Call<UserOfferChatDto> call, Response<UserOfferChatDto> response) {
                    if (response.isSuccessful()) {
                        ((InvitationsFragment.Communicate) context).accepted(chat.getId(), response.body().getId());
                    } else {
                        errorService.handle(context, response);
                    }
                }

                @Override
                public void onFailure(Call<UserOfferChatDto> call, Throwable t) {
                    toast("Check your internet connection");
                }
            }, chat.getId());
        });
    }

    @Override
    public int getItemCount() {
        return chatDtos.size();
    }

    public void toast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}