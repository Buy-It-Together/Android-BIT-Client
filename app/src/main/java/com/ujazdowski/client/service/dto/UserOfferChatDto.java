package com.ujazdowski.client.service.dto;

import java.io.Serializable;

public class UserOfferChatDto implements Serializable {

    private Long id;

    private Boolean accepted;

    private UserOfferDto userOfferVM;

    private Boolean my;

    public UserOfferChatDto(Long id, Boolean accepted, UserOfferDto userOfferVM, Boolean my) {
        this.id = id;
        this.accepted = accepted;
        this.userOfferVM = userOfferVM;
        this.my = my;
    }

    public UserOfferChatDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public UserOfferDto getUserOfferVM() {
        return userOfferVM;
    }

    public void setUserOfferVM(UserOfferDto userOfferVM) {
        this.userOfferVM = userOfferVM;
    }

    public Boolean getMy() {
        return my;
    }

    public void setMy(Boolean my) {
        this.my = my;
    }
}
