package com.ujazdowski.client.service;

import com.ujazdowski.client.api.AuthenticationApi;
import com.ujazdowski.client.api.requests.AuthenticateRequest;
import com.ujazdowski.client.api.requests.RegisterRequest;
import com.ujazdowski.client.api.response.JwtAuthenticationResponse;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;
import java.io.IOException;

public class AuthenticateService extends ApiService {
    @Inject
    public AuthenticateService() {
        super(AuthenticationApi.class, false);
    }

    public void token(String token) {
        ApiService.setToken(token);
    }

    public boolean authenticate(String login, String password, Boolean rememberMe) throws IOException {
        Response<JwtAuthenticationResponse> execute = getApi().authenticate(new AuthenticateRequest(login, password, rememberMe)).execute();
        if (execute.isSuccessful()) {
            ApiService.setToken(execute.body().getId_token());
            return true;
        }
        return false;
    }

    public void register(Callback<Void> callback, String login, String password){
        getApi().register(new RegisterRequest(login, password)).enqueue(callback);
    }

    @Override
    public AuthenticationApi getApi() {
        return (AuthenticationApi) super.api;
    }
}
