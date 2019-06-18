package com.myapp.test.weathermap.repository;


import com.myapp.test.weathermap.MainContract;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainRepository implements MainContract.Repository {
    private String result;
    private String resultList;
    private String timeZone;
    private String layer;

    @Override
    public String loadTwoWeeksWeather(String latitude, String longitude) {

        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude + "&appid=db2c05a113d582c380b53504bc58b1fd")
                .build();
        try {
            Response response = client.newCall(request).execute();
            resultList = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public String loadCurrentWeather(String latitude, String longitude) {
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=db2c05a113d582c380b53504bc58b1fd")
                .build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String loadTimeZone(){
        final OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url("https://tilecache.rainviewer.com/api/maps.json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            timeZone = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return timeZone;
    }

    @Override
    public String loadLayer(String timeZone, String zoom, String x, String y){
        final OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url("https://tilecache.rainviewer.com/v2/radar/" + timeZone + "/512/" + zoom +"/" + x +"/" + y + ".png")
                .build();
        try {
            Response response = client.newCall(request).execute();
            layer = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return layer;
    }



}
