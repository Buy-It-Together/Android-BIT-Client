package com.ujazdowski.client.api.requests;

import java.io.Serializable;

public class NewMessageRequest implements Serializable {
    private String message;

    public NewMessageRequest(String message) {
        this.message = message;
    }

    public NewMessageRequest() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

