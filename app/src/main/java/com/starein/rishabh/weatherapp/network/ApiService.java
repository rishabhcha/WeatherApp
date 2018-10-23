package com.starein.rishabh.weatherapp.network;

import com.starein.rishabh.weatherapp.model.Weather;
import com.starein.rishabh.weatherapp.util.Constants;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ApiService {

    private ApiInterface apiInterface;
    private String cityName;

    public ApiService(ApiInterface apiInterface, String cityName) {
        this.apiInterface = apiInterface;
        this.cityName = cityName;
    }

    public Disposable weatherForecast(NetworkCallback<Weather> callback) {
        return apiInterface.getForecastWeather(Constants.API_KEY, cityName, Constants.DAYS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        callback::onSuccess,
                        callback::onError
                );

    }

}
