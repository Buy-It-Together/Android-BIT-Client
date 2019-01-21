package com.ujazdowski.client.api;

import com.ujazdowski.client.api.requests.AuthenticateRequest;
import com.ujazdowski.client.api.requests.RegisterRequest;
import com.ujazdowski.client.api.response.JwtAuthenticationResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationApi {
    @POST("authenticate")
    Call<JwtAuthenticationResponse> authenticate(@Body AuthenticateRequest request);

    @POST("register")
    Call<Void> register(@Body RegisterRequest registerRequest);
}
