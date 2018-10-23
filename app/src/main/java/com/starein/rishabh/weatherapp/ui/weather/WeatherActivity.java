package com.starein.rishabh.weatherapp.ui.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.starein.rishabh.weatherapp.R;
import com.starein.rishabh.weatherapp.adapter.WeatherAdapter;
import com.starein.rishabh.weatherapp.deps.provider.ApplicationProvider;
import com.starein.rishabh.weatherapp.model.Forecastday;
import com.starein.rishabh.weatherapp.model.Weather;
import com.starein.rishabh.weatherapp.network.ApiService;

import java.util.List;

import javax.inject.Inject;

import static java.security.AccessController.getContext;

public class WeatherActivity extends AppCompatActivity implements WeatherContract.View{

    @Inject
    ApiService service;

    private WeatherContract.Presenter presenter;

    private RecyclerView weatherRecyclerView;
    private ProgressBar progressBar;
    private WeatherAdapter weatherAdapter;
    private TextView currTempTextView, cityTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ((ApplicationProvider)this.getApplication()).providesApplicationComponent().inject(this);

        initViews();

        presenter = new WeatherPresenter(this);

    }

    private void initViews() {

        weatherRecyclerView = findViewById(R.id.weatherRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(WeatherActivity.this);
        weatherRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(weatherRecyclerView.getContext(),
                layoutManager.getOrientation());
        weatherRecyclerView.addItemDecoration(dividerItemDecoration);
        weatherAdapter = new WeatherAdapter();
        weatherRecyclerView.setAdapter(weatherAdapter);

        progressBar = findViewById(R.id.progressBar);
        currTempTextView = findViewById(R.id.currTempTextView);
        cityTextView = findViewById(R.id.cityTextView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadForecastWeather(service);
    }

    @Override
    protected void onDestroy() {
        presenter.stop();
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAPISuccess(String currTemp, String cityName, List<Forecastday> forecastdayList) {

        currTempTextView.setVisibility(View.VISIBLE);
        currTempTextView.setText(String.format("%s%s", currTemp, getResources().getString(R.string.degree)));
        cityTextView.setVisibility(View.VISIBLE);
        cityTextView.setText(cityName);

        weatherAdapter.setForecastList(forecastdayList);
        weatherAdapter.notifyDataSetChanged();

        Animation bottomUp = AnimationUtils.loadAnimation(WeatherActivity.this, R.anim.bottom_up);
        weatherRecyclerView.startAnimation(bottomUp);
        weatherRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAPIFailure() {
        setContentView(R.layout.activity_weather_error);
    }

    @Override
    public void setPresenter(WeatherContract.Presenter presenter) {

    }

    public void retryLoadingWeather(View view) {
        setContentView(R.layout.activity_weather);
        initViews();
        presenter.loadForecastWeather(service);
    }
}
