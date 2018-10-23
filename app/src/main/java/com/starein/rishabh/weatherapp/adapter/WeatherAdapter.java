package com.starein.rishabh.weatherapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.starein.rishabh.weatherapp.R;
import com.starein.rishabh.weatherapp.model.Forecastday;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherViewHolder> {

    private List<Forecastday> forecastdayList;

    public WeatherAdapter(){

    }

    public void setForecastList(List<Forecastday> forecastdayList){
        this.forecastdayList = forecastdayList;
    }



    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_weather, viewGroup, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder weatherViewHolder, int i) {

        weatherViewHolder.dayTextView.setText(getDayFromDate(forecastdayList.get(i).getDate()));
        weatherViewHolder.tempTextView.setText(String.format("%s C", (int) Math.round(forecastdayList.get(i).getDay().getAvgtempC())));

    }

    private String getDayFromDate(String input){
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = inFormat.parse(input);
            SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
            return outFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public int getItemCount() {
        return forecastdayList != null ? forecastdayList.size() : 0;
    }

}


class WeatherViewHolder extends RecyclerView.ViewHolder{

    public TextView dayTextView, tempTextView;

    public WeatherViewHolder(@NonNull View itemView) {
        super(itemView);

        dayTextView = itemView.findViewById(R.id.dayTextView);
        tempTextView = itemView.findViewById(R.id.tempTextView);

    }

}