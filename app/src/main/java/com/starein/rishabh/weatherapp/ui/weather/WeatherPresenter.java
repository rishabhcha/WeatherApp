package com.starein.rishabh.weatherapp.ui.weather;

import com.starein.rishabh.weatherapp.model.Forecastday;
import com.starein.rishabh.weatherapp.model.Weather;
import com.starein.rishabh.weatherapp.network.ApiService;
import com.starein.rishabh.weatherapp.network.NetworkCallback;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class WeatherPresenter implements WeatherContract.Presenter{

    private WeatherContract.View view;
    private CompositeDisposable compositeDisposable;

    public WeatherPresenter(WeatherContract.View view){

        this.view = view;
        compositeDisposable = new CompositeDisposable();

    }


    @Override
    public void loadForecastWeather(ApiService apiService) {

        Disposable disposable = apiService.weatherForecast(new NetworkCallback<Weather>() {
            @Override
            public void onSuccess(Weather response) {
                List<Forecastday> forecastdayList = response.getForecast().getForecastday();
                forecastdayList.remove(0);
                view.onAPISuccess(String.valueOf((int) Math.round(response.getCurrent().getTempC())),
                        response.getLocation().getName(), forecastdayList);
                view.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                view.onAPIFailure();
                view.hideLoading();
            }
        });
        compositeDisposable.add(disposable);

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        view = null;
        compositeDisposable.clear();
    }

}
