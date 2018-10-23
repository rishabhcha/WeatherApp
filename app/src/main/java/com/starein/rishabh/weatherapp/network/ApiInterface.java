package com.starein.rishabh.weatherapp.network;

import com.starein.rishabh.weatherapp.model.Weather;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("forecast.json")
    Observable<Weather> getForecastWeather(
            @Query("key") String api_key,
            @Query("q") String q,
            @Query("days") String days
    );

}
