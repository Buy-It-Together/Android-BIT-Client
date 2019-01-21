package com.ujazdowski.client.service.dto;

import java.io.Serializable;
import java.util.Date;

public class MessageDto implements Serializable {
    private String email;
    private String message;
    private Date date;

    public MessageDto(String email, String message, Date date) {
        this.email = email;
        this.message = message;
        this.date = date;
    }

    public MessageDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
