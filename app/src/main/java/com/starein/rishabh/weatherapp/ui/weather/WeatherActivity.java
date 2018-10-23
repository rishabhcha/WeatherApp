package com.starein.rishabh.weatherapp.ui.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.starein.rishabh.weatherapp.R;
import com.starein.rishabh.weatherapp.adapter.WeatherAdapter;
import com.starein.rishabh.weatherapp.deps.provider.ApplicationProvider;
import com.starein.rishabh.weatherapp.model.Forecastday;
import com.starein.rishabh.weatherapp.network.ApiService;

import java.util.List;

import javax.inject.Inject;

public class WeatherActivity extends AppCompatActivity implements WeatherContract.View{

    @Inject
    ApiService service;

    private WeatherContract.Presenter presenter;

    private RecyclerView weatherRecyclerView;
    private ProgressBar progressBar;
    private WeatherAdapter weatherAdapter;

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
        weatherRecyclerView.setLayoutManager(new LinearLayoutManager(WeatherActivity.this));
        weatherAdapter = new WeatherAdapter();
        weatherRecyclerView.setAdapter(weatherAdapter);

        progressBar = findViewById(R.id.progressBar);

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
    public void setDataToRecyclerView(List<Forecastday> forecastdayArrayList) {
        weatherAdapter.setForecastList(forecastdayArrayList);
        weatherAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailureGetData(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(WeatherContract.Presenter presenter) {

    }
}
