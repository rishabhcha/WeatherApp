package com.starein.rishabh.weatherapp.deps.module;

import com.starein.rishabh.weatherapp.network.ApiInterface;
import com.starein.rishabh.weatherapp.network.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @Provides
    @Singleton
    protected ApiService providesAndroidService(ApiInterface apiInterface){
        return new ApiService(apiInterface);
    }
}
