package com.myapp.test.weathermap.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myapp.test.weathermap.contract.MainContract;
import com.myapp.test.weathermap.entity.fiveDayWeather.WeatherInfo;
import com.myapp.test.weathermap.entity.fiveDayWeather.WeatherInfoList;
import com.myapp.test.weathermap.repository.MainRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ListPresenter implements MainContract.ListPresenter, MainContract.Repository.OnFinishedListener {
    private MainContract.ListWeatherView mView;
    private MainRepository mRepository;
    private WeatherInfoList weatherInfo;
    private ArrayList<WeatherInfo> weatherInfoArrayList;
    private ArrayList<WeatherInfo> day1;
    private ArrayList<WeatherInfo> day2;
    private ArrayList<WeatherInfo> day3;
    private ArrayList<WeatherInfo> day4;
    private ArrayList<WeatherInfo> day5;
    private int count = 0;

    public ListPresenter(MainContract.ListWeatherView mView) {
        this.mView = mView;
        this.mRepository = new MainRepository();

    }

    @Override
    public void onActivityCreated() {
        mRepository.loadFiveDayWeather(mView.getLatitude(), mView.getLongitude(), this);
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

        return new Object[]{day1, day2, day3, day4, day5};
    }

    private String getDate(long dt) {
        return new SimpleDateFormat("DD-MM-YY").format(new Date
                (dt * 1000));

    }

    @Override
    public void onFinished(String result) {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        weatherInfo = gson.fromJson(result, WeatherInfoList.class);


        if (Integer.parseInt(weatherInfo.getCod()) == 200) {
            weatherInfoArrayList = new ArrayList<>(Arrays.asList(weatherInfo.getList()));
            Object[] days = sortByDay(weatherInfoArrayList);
            mView.showFiveDayWeather((ArrayList<WeatherInfo>)days[0], (ArrayList<WeatherInfo>)days[1],
                    (ArrayList<WeatherInfo>)days[2], (ArrayList<WeatherInfo>)days[3],
                    (ArrayList<WeatherInfo>)days[4]);
        }

    }

    @Override
    public void onFailure(String error) {
        mView.showError(error);
    }

    @Override
    public void showNoConnection() {
        mView.showNoConnection();
    }
}
