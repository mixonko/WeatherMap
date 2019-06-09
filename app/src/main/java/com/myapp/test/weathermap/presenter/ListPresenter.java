package com.myapp.test.weathermap.presenter;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myapp.test.weathermap.MainContract;
import com.myapp.test.weathermap.presenter.model.TwoWeeksWeather.WeatherInfo;
import com.myapp.test.weathermap.presenter.model.TwoWeeksWeather.WeatherInfoList;
import com.myapp.test.weathermap.repository.MainRepository;
import com.myapp.test.weathermap.view.ExampleAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class ListPresenter implements MainContract.ListPresenter {
    private MainContract.ListWeatherView mView;
    private MainRepository mRepository;
    private WeatherInfoList weatherInfo;
    private String result;
    private ArrayList<WeatherInfo> weatherInfoArrayList;

    public ListPresenter(MainContract.ListWeatherView mView) {
        this.mView = mView;
        this.mRepository = new MainRepository();

    }

    @Override
    public void onActivityCreated(final String latitude, final String longitude) {

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                result = mRepository.loadTwoWeeksWeather(latitude, longitude);
                return result;
            }
            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                View(String.valueOf(o));

            }
        }.execute();

    }

    private void View(String res) {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        weatherInfo = gson.fromJson(res, WeatherInfoList.class);


        if (Integer.parseInt(weatherInfo.getCod()) == 200) {
            weatherInfoArrayList = new ArrayList<>(Arrays.asList(weatherInfo.getList()));
            ExampleAdapter exampleAdapter = new ExampleAdapter(weatherInfoArrayList);
            mView.showTwoWeeksWeather(exampleAdapter);


        }


    }


}
