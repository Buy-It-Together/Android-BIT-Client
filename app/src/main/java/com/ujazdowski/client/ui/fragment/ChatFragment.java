package com.ujazdowski.client.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.ujazdowski.client.R;
import com.ujazdowski.client.api.requests.NewMessageRequest;
import com.ujazdowski.client.service.MessageService;
import com.ujazdowski.client.service.dto.ChatDto;
import com.ujazdowski.client.service.dto.MessageDto;
import com.ujazdowski.client.service.geocoder.GeocoderAbstractService;
import com.ujazdowski.client.ui.adapter.MessageAdapter;
import dagger.android.support.DaggerFragment;

import javax.inject.Inject;

public class ChatFragment extends DaggerFragment implements Title {
    @Inject
    MessageService messageService;
    @Inject
    GeocoderAbstractService geocoderService;
    @BindView(R.id.fragment_chat_message_recycle_view)
    RecyclerView messageRecycleView;
    @BindView(R.id.fragment_chat_message)
    EditText message;
    @BindView(R.id.button_chatbox_send)
    Button sendMessage;

    private RecyclerView.LayoutManager layoutManager;
    private MessageAdapter adapter;
    private ChatDto chat;

    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);
        messageRecycleView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        messageRecycleView.setLayoutManager(layoutManager);
        adapter = new MessageAdapter(getActivity());
        messageRecycleView.setAdapter(adapter);
        adapter.setChat(chat);

        connect();

        sendMessage.setOnClickListener(v -> {
            if (!message.getText().toString().isEmpty()) {
                messageService.sendMessage(chat.getId(), new NewMessageRequest(message.getText().toString()));
                message.setText("");
            }
        });

        return view;
    }

    public void connect() {
        if (!messageService.isConnected()) {
            messageService.connect();
            messageService.listenChat(chat.getId(), message -> {
                MessageDto messageDto = new Gson().fromJson(message.getPayload(), MessageDto.class);
                adapter.addMessage(messageDto);
                layoutManager.scrollToPosition(chat.getMessages().size());
            });
        }
    }

    @Override
    public int getTitleResource() {
        return R.string.chat_page_title;
    }

    public void setChat(ChatDto chat) {
        this.chat = chat;
    }

    @Override
    public void onDestroy() {
        messageService.disconnect();
        super.onDestroy();
    }
}
