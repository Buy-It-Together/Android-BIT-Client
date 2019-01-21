package com.ujazdowski.client.service;

import android.app.Activity;
import android.content.Intent;
import com.ujazdowski.client.ui.activity.LoginActivity;
import com.ujazdowski.client.repository.SharedPreferencesManager;
import retrofit2.Response;

import javax.inject.Inject;
import java.net.HttpURLConnection;

public class ErrorService {

    @Inject
    public ErrorService() {
    }

    public void handle(Activity context, Response response) {
        switch (response.code()) {
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                logout(context);
                return;
        }
    }

    public void logout(Activity context) {
        SharedPreferencesManager.removeToken();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        context.finish();
    }
}
