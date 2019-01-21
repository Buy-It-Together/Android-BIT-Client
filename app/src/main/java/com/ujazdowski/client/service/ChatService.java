package com.ujazdowski.client.service;

import com.ujazdowski.client.api.ChatApi;
import com.ujazdowski.client.api.requests.NewMessageRequest;
import com.ujazdowski.client.service.dto.ChatDto;
import com.ujazdowski.client.service.dto.MessageDto;
import com.ujazdowski.client.service.dto.UserOfferChatDto;
import retrofit2.Callback;

import javax.inject.Inject;
import java.util.Set;

public class ChatService extends ApiService {
    @Inject
    public ChatService() {
        super(ChatApi.class, true);
    }

    public void getChatsByOfferId(Callback<Set<ChatDto>> callback, Long offerId) {
        getApi().getChatsByOfferId(offerId).enqueue(callback);
    }

    @Override
    public ChatApi getApi() {
        return (ChatApi) super.api;
    }

    public void accept(Callback<UserOfferChatDto> callback, Long chatId) {
        getApi().acceptChat(chatId).enqueue(callback);
    }

    public void sendMessage(Callback<MessageDto> callback, Long chatId, String message) {
        getApi().sendMessage(chatId, new NewMessageRequest(message.trim())).enqueue(callback);
    }
}
