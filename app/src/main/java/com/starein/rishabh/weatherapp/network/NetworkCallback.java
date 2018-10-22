package com.starein.rishabh.weatherapp.network;

public interface NetworkCallback<T> {
    void onSuccess(T response);
    void onError(Throwable e);
}
