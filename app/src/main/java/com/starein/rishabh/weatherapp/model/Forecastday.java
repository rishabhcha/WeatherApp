package com.starein.rishabh.weatherapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Forecastday {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("date_epoch")
    @Expose
    private Integer dateEpoch;
    @SerializedName("day")
    @Expose
    private Day day;
    @SerializedName("astro")
    @Expose
    private Astro astro;

    /**
     * No args constructor for use in serialization
     */
    public Forecastday() {
    }

    /**
     * @param astro
     * @param dateEpoch
     * @param day
     * @param date
     */
    public Forecastday(String date, Integer dateEpoch, Day day, Astro astro) {
        super();
        this.date = date;
        this.dateEpoch = dateEpoch;
        this.day = day;
        this.astro = astro;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getDateEpoch() {
        return dateEpoch;
    }

    public void setDateEpoch(Integer dateEpoch) {
        this.dateEpoch = dateEpoch;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Astro getAstro() {
        return astro;
    }

    public void setAstro(Astro astro) {
        this.astro = astro;
    }
}