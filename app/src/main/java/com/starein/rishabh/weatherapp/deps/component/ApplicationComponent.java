package com.starein.rishabh.weatherapp.deps.component;

import com.starein.rishabh.weatherapp.ui.MainActivity;
import com.starein.rishabh.weatherapp.deps.module.NetworkModule;
import com.starein.rishabh.weatherapp.deps.module.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, ServiceModule.class})
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
}
