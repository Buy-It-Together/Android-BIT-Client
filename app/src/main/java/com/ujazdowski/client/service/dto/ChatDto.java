package com.ujazdowski.client.service.dto;

import com.ujazdowski.client.utils.MessageSortedList;

import java.io.Serializable;
import java.util.*;

public class ChatDto implements Serializable {

    /**
     * id chatu
     */
    private Long id;

    /**
     * Lista szystkich ofert - różni użytkownicy
     */
    private Set<UserOfferChatDto> userOfferChatVMs;

    private MessageSortedList messages = new MessageSortedList();

    private String suggested_latitude;

    private String suggested_longitude;

    public ChatDto() {
    }

    public ChatDto(Long id, Set<UserOfferChatDto> userOfferChatVMs, MessageSortedList messages, String suggested_latitude, String suggested_longitude) {
        this.id = id;
        this.userOfferChatVMs = userOfferChatVMs;
        this.messages = messages;
        this.suggested_latitude = suggested_latitude;
        this.suggested_longitude = suggested_longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<UserOfferChatDto> getUserOfferChatVMs() {
        return userOfferChatVMs;
    }

    public void setUserOfferChatVMs(Set<UserOfferChatDto> userOfferChatVMs) {
        this.userOfferChatVMs = userOfferChatVMs;
    }

    public String getSuggested_latitude() {
        return suggested_latitude;
    }

    public void setSuggested_latitude(String suggested_latitude) {
        this.suggested_latitude = suggested_latitude;
    }

    public String getSuggested_longitude() {
        return suggested_longitude;
    }

    public void setSuggested_longitude(String suggested_longitude) {
        this.suggested_longitude = suggested_longitude;
    }

    public List<MessageDto> getMessages() {
        return messages;
    }

    public void setMessages(MessageSortedList messages) {
        this.messages = messages;
    }
}
