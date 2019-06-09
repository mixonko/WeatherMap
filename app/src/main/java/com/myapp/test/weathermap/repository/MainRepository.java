package com.myapp.test.weathermap.repository;

import android.os.AsyncTask;

import com.myapp.test.weathermap.MainContract;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainRepository implements MainContract.Repository {
    private String result;
    private String resultList;

    @Override
    public String loadTwoWeeksWeather(String latitude, String longitude) throws ExecutionException, InterruptedException {


        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude + "&appid=db2c05a113d582c380b53504bc58b1fd")
                .build();


        try {
            Response response = client.newCall(request).execute();
            resultList = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
            resultList = String.valueOf(e);
        }


        return result;
    }

    @Override
    public String loadCurrentWeather(String latitude, String longitude) throws ExecutionException, InterruptedException {
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=db2c05a113d582c380b53504bc58b1fd")
                .build();


        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
            result = String.valueOf(e);
        }


        return result;

    }


}
