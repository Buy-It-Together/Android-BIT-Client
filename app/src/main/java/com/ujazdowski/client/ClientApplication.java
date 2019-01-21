package com.ujazdowski.client;

import com.ujazdowski.client.dagger.AppComponent;
import com.ujazdowski.client.dagger.DaggerAppComponent;
import com.ujazdowski.client.repository.SharedPreferencesManager;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import io.realm.Realm;

public class ClientApplication extends DaggerApplication {

    public ClientApplication() {
    }

    @Override
    protected AndroidInjector<ClientApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        Realm.init(getApplicationContext());
        SharedPreferencesManager.setApplicationContext(this.getApplicationContext());
        return appComponent;
    }
}
