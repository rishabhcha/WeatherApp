package com.starein.rishabh.weatherapp;

import android.app.Application;

import com.starein.rishabh.weatherapp.deps.component.ApplicationComponent;
import com.starein.rishabh.weatherapp.deps.component.DaggerApplicationComponent;
import com.starein.rishabh.weatherapp.deps.module.LocationModule;
import com.starein.rishabh.weatherapp.deps.module.NetworkModule;
import com.starein.rishabh.weatherapp.deps.module.ServiceModule;
import com.starein.rishabh.weatherapp.deps.provider.ApplicationProvider;

public class MyApp extends Application implements ApplicationProvider {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .networkModule(new NetworkModule(this))
                .serviceModule(new ServiceModule(this))
                .locationModule(new LocationModule(this))
                .build();
    }

    @Override
    public ApplicationComponent providesApplicationComponent() {
        return applicationComponent;
    }
}
