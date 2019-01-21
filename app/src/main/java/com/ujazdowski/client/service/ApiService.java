package com.ujazdowski.client.service;

import android.support.annotation.NonNull;
import com.google.gson.GsonBuilder;
import com.ujazdowski.client.utils.DateUtils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class ApiService<T> {
    public final static String BASE_ADDRESS = "192.168.0.2";
    private final static String BASE_URL = new StringBuilder().append("http://").append(BASE_ADDRESS).append("/api/").toString();
    private static String token = new String();

    protected final T api;

    public ApiService(Class<T> api) {
        this(api, !token.isEmpty());
    }

    public ApiService(Class<T> api, boolean authenticated) {
        this(api, authenticated, defaultHeaders());
    }

    public ApiService(Class<T> api, boolean authenticated, Map<String, String> headers) {
        if (authenticated) {
            headers.put("Authorization", getAuth());
        }
        this.api = getClient(headers).create(api);
    }

    public static String getToken() {
        return token;
    }

    protected static void setToken(String token) {
        ApiService.token = token;
    }

    synchronized private static Retrofit getClient(final Map<String, String> headers) {
        return new Retrofit.Builder().client(new OkHttpClient.Builder().addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    builder.addHeader(entry.getKey(), entry.getValue());
                }
                Request request = builder.build();
                return chain.proceed(request);
            }
        }).build()).addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                .setDateFormat(DateUtils.INSTANT_DATE_FORMAT).create())).baseUrl(BASE_URL).build();
    }

    protected static Map<String, String> defaultHeaders() {
        Map<String, String> defaultHeaders = new HashMap<>();
        defaultHeaders.put("Accept-Language", Locale.getDefault().getDisplayLanguage());
        defaultHeaders.put("Content-Type", "Application/json");
        return defaultHeaders;
    }

    public static String getAuth() {
        return new StringBuilder().append("Bearer ").append(ApiService.getToken()).toString();
    }

    public abstract T getApi();
}
