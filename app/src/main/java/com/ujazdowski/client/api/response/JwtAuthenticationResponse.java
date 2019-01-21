package com.ujazdowski.client.api.response;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {

    private String id_token;
    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(String token) {
        this.id_token = token;
    }

    public JwtAuthenticationResponse(String id_token, String tokenType) {
        this.id_token = id_token;
        this.tokenType = tokenType;
    }

    public JwtAuthenticationResponse() {
    }

    public String getId_token() {
        return id_token;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}