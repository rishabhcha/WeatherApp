package com.starein.rishabh.weatherapp.ui.weather;

import com.starein.rishabh.weatherapp.model.Forecastday;
import com.starein.rishabh.weatherapp.model.Weather;
import com.starein.rishabh.weatherapp.network.ApiService;
import com.starein.rishabh.weatherapp.ui.BasePresenter;
import com.starein.rishabh.weatherapp.ui.BaseView;

import java.util.ArrayList;
import java.util.List;

public interface WeatherContract {

    interface View extends BaseView<Presenter> {

        void showLoading();

        void hideLoading();

        void onAPISuccess(String currTemp, String cityName, List<Forecastday> forecastdayList);

        void onAPIFailure();
    }

    interface Presenter extends BasePresenter {

        void loadForecastWeather(ApiService apiService);
    }

}
