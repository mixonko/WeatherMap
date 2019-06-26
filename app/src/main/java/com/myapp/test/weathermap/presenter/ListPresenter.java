package com.myapp.test.weathermap.presenter;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myapp.test.weathermap.MainContract;
import com.myapp.test.weathermap.presenter.model.fiveDayWeather.WeatherInfo;
import com.myapp.test.weathermap.presenter.model.fiveDayWeather.WeatherInfoList;
import com.myapp.test.weathermap.repository.MainRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ListPresenter implements MainContract.ListPresenter {
    private MainContract.ListWeatherView mView;
    private MainRepository mRepository;
    private WeatherInfoList weatherInfo;
    private String result;
    private ArrayList<WeatherInfo> weatherInfoArrayList;
    private ArrayList<WeatherInfo> day1;
    private ArrayList<WeatherInfo> day2;
    private ArrayList<WeatherInfo> day3;
    private ArrayList<WeatherInfo> day4;
    private ArrayList<WeatherInfo> day5;
    private ArrayList<WeatherInfo> day6;
    private int count = 0;

    public ListPresenter(MainContract.ListWeatherView mView) {
        this.mView = mView;
        this.mRepository = new MainRepository();

    }

    @Override
    public void onActivityCreated(final String latitude, final String longitude) {
        getWeatherInfo(latitude, longitude);
    }

    private void getWeatherInfo(final String latitude, final String longitude) {
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                result = mRepository.loadFiveDayWeather(latitude, longitude);
                return result;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                showWeather(String.valueOf(o));

            }
        }.execute();
    }

    private void showWeather(String res) {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        weatherInfo = gson.fromJson(res, WeatherInfoList.class);


        if (Integer.parseInt(weatherInfo.getCod()) == 200) {
            weatherInfoArrayList = new ArrayList<>(Arrays.asList(weatherInfo.getList()));
            Object[] days = sortByDay(weatherInfoArrayList);
            mView.showFiveDayWeather(days);
        }


    }

    private Object[] sortByDay(ArrayList<WeatherInfo> list) {

        day1 = new ArrayList<>();
        day2 = new ArrayList<>();
        day3 = new ArrayList<>();
        day4 = new ArrayList<>();
        day5 = new ArrayList<>();

        day1.add(list.get(0));
        for (int i = count + 1; i < list.size(); i++) {
            if (getDate(list.get(i).getDt()).equals(getDate(day1.get(0).getDt()))) {
                day1.add(list.get(i));
                count = i;
            }
        }
        day2.add(list.get(count + 1));
        for (int i = count + 2; i < list.size(); i++) {
            if (getDate(list.get(i).getDt()).equals(getDate(day2.get(0).getDt()))) {
                day2.add(list.get(i));
                count = i;
            }
        }
        day3.add(list.get(count + 1));
        for (int i = count + 2; i < list.size(); i++) {
            if (getDate(list.get(i).getDt()).equals(getDate(day3.get(0).getDt()))) {
                day3.add(list.get(i));
                count = i;
            }
        }
        day4.add(list.get(count + 1));
        for (int i = count + 2; i < list.size(); i++) {
            if (getDate(list.get(i).getDt()).equals(getDate(day4.get(0).getDt()))) {
                day4.add(list.get(i));
                count = i;
            }
        }
        day5.add(list.get(count + 1));
        for (int i = count + 2; i < list.size(); i++) {
            if (getDate(list.get(i).getDt()).equals(getDate(day5.get(0).getDt()))) {
                day5.add(list.get(i));
                count = i;
            }
        }
        if (count < list.size() - 1) {
            day6 = new ArrayList<>();
            day6.add(list.get(count + 1));

            for (int i = count + 2; i < list.size(); i++) {
                if (getDate(list.get(i).getDt()).equals(getDate(day6.get(0).getDt()))) {
                    day6.add(list.get(i));
                    count = i;
                }
            }
            return new Object[]{day1, day2, day3, day4, day5, day6};
        }
        return new Object[]{day1, day2, day3, day4, day5};
    }

    private String getDate(long dt) {
        return new SimpleDateFormat("dd").format(new Date
                (dt * 1000));

    }

}
