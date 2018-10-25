package com.starein.rishabh.weatherapp.ui.weather;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.starein.rishabh.weatherapp.util.PermissionsDialogFragment;

import java.util.List;

import javax.inject.Inject;

import static java.security.AccessController.getContext;


public class WeatherActivity extends AppCompatActivity implements WeatherContract.View,
        PermissionsDialogFragment.PermissionsGrantedCallback {

    @Inject
    ApiService service;

    private WeatherContract.Presenter presenter;

    private RecyclerView weatherRecyclerView;
    private ProgressBar progressBar;
    private WeatherAdapter weatherAdapter;
    private TextView currTempTextView, cityTextView;

    //Maintains a counter of active tasks. When the counter is zero, the associated resource is considered idle.
    CountingIdlingResource espressoTestIdlingResource = new CountingIdlingResource("Network_Call");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ((ApplicationProvider)this.getApplication()).providesApplicationComponent().inject(this);

        initViews();

        presenter = new WeatherPresenter(this);

    }

    //initialize all views
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
        if (isPermissionGranted()){//If location permission is granted show forecast
            showForecastWeather();
        }else {
            PermissionsDialogFragment.newInstance().show(getSupportFragmentManager(), PermissionsDialogFragment.class.getName());
        }
    }

    @Override
    protected void onDestroy() {
        presenter.stop();
        super.onDestroy();
    }

    @Override
    public void showForecastWeather() {
        presenter.loadForecastWeather(service);
        espressoTestIdlingResource.increment();
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

        espressoTestIdlingResource.decrement();
    }

    @Override
    public void onAPIFailure() {
        setContentView(R.layout.activity_weather_error);
        espressoTestIdlingResource.decrement();
    }

    @Override
    public void setPresenter(WeatherContract.Presenter presenter) {

    }

    //on click of retry button
    public void retryLoadingWeather(View view) {
        setContentView(R.layout.activity_weather);
        initViews();
        showForecastWeather();
    }

    //return counter of active task
    public CountingIdlingResource getEspressoIdlingResourceForMainActivity() {
        return espressoTestIdlingResource;
    }

    //check for location permission
    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
}
