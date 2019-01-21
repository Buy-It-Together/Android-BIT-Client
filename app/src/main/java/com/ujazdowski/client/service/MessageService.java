package com.ujazdowski.client.service;

import com.google.gson.Gson;
import com.ujazdowski.client.api.requests.NewMessageRequest;
import io.reactivex.CompletableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;
import ua.naiksoftware.stomp.client.StompMessage;

import javax.inject.Inject;
import java.util.Collections;

public class MessageService {

    private final static String mAddress = new StringBuilder().append("ws://").append(ApiService.BASE_ADDRESS).append("/live-chat/websocket").toString();

    private StompClient mStompClient;

    @Inject
    public MessageService() {
    }

    public boolean isConnected() {
        if (mStompClient != null) {
            return mStompClient.isConnected();
        }
        return false;
    }

    public void connect() {
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, mAddress, Collections.singletonMap("Authorization", ApiService.getAuth()));
        mStompClient.lifecycle().subscribe();
        mStompClient.connect();
    }

    public void listenChat(Long chatId, Consumer<StompMessage> newMessage) {
        mStompClient.topic("/topic/chat-messages/" + chatId)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(newMessage);
    }

    public void sendMessage(Long chatId, NewMessageRequest messageRequest) {
        mStompClient.send("/topic/chat/send/" + chatId, new Gson().toJson(messageRequest))
                .compose(applySchedulers()).subscribe();
    }

    protected CompletableTransformer applySchedulers() {
        return upstream -> upstream
                .unsubscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void disconnect() {
        if (mStompClient != null) {
            mStompClient.disconnect();
        }
    }
}
