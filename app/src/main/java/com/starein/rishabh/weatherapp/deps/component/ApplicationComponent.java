package com.starein.rishabh.weatherapp.deps.component;

import com.starein.rishabh.weatherapp.deps.module.LocationModule;
import com.starein.rishabh.weatherapp.ui.weather.WeatherActivity;
import com.starein.rishabh.weatherapp.deps.module.NetworkModule;
import com.starein.rishabh.weatherapp.deps.module.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, ServiceModule.class, LocationModule.class})
public interface ApplicationComponent {
    void inject(WeatherActivity weatherActivity);
}
