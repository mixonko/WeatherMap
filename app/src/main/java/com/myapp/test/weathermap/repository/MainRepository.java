package com.myapp.test.weathermap.repository;


import android.os.AsyncTask;

import com.myapp.test.weathermap.contract.MainContract;
import com.myapp.test.weathermap.myApplicationApp.MyApplication;
import com.myapp.test.weathermap.utils.InternetConnection;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainRepository implements MainContract.Repository {
    private String result;

    @Override
    public void loadFiveDayWeather(String latitude, String longitude, final OnFinishedListener onFinishedListener) {

        if (InternetConnection.checkConnection(MyApplication.getAppContext())){
            final OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url("https://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude + "&appid=db2c05a113d582c380b53504bc58b1fd")
                    .build();

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    try {
                        Response response = client.newCall(request).execute();
                        result = response.body().string();
                    } catch (IOException e) {
                        onFinishedListener.onFailure(String.valueOf(e));
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
                    onFinishedListener.onFinished(String.valueOf(o));
                }
            }.execute();
        }else {
            onFinishedListener.showNoConnection();
        }

    }

    @Override
    public void loadCurrentWeather(String latitude, String longitude, final OnFinishedListener onFinishedListener) {
        if (InternetConnection.checkConnection(MyApplication.getAppContext())){
            final OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=db2c05a113d582c380b53504bc58b1fd")
                    .build();

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    try {
                        Response response = client.newCall(request).execute();
                        result = response.body().string();
                    } catch (IOException e) {
                        onFinishedListener.onFailure(String.valueOf(e));
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
                    onFinishedListener.onFinished(String.valueOf(o));
                }
            }.execute();
        }else {
            onFinishedListener.showNoConnection();
        }
    }
}
