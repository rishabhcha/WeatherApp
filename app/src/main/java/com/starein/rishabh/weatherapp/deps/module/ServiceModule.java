package com.starein.rishabh.weatherapp.deps.module;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.starein.rishabh.weatherapp.network.ApiInterface;
import com.starein.rishabh.weatherapp.network.ApiService;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    private Context context;

    public ServiceModule(Context context){
        this.context = context;
    }

    @Provides
    @Singleton
    protected ApiService providesAndroidService(ApiInterface apiInterface, Location location){
        return new ApiService(apiInterface, getCityNameFromLocation(location));
    }

    private String getCityNameFromLocation(Location location){
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.size() > 0) {
                return addresses.get(0).getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
