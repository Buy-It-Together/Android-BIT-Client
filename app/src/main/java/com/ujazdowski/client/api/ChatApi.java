package com.ujazdowski.client.api;

import com.ujazdowski.client.api.requests.NewMessageRequest;
import com.ujazdowski.client.service.dto.ChatDto;
import com.ujazdowski.client.service.dto.MessageDto;
import com.ujazdowski.client.service.dto.UserOfferChatDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.Set;

public interface ChatApi {
    @GET("chats-by-user-offer/{offerId}")
    Call<Set<ChatDto>> getChatsByOfferId(@Path("offerId") Long offerId);

    @GET("chat/accept/{id}")
    Call<UserOfferChatDto> acceptChat(@Path("id") Long id);

    @POST("chat/message/{id}")
    Call<MessageDto> sendMessage(@Path("id") Long id, @Body NewMessageRequest message);
}
