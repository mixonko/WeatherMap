package com.myapp.test.weathermap;

import java.util.concurrent.ExecutionException;

public interface MainContract  {

    interface View {
        void showCurrentWeather(String temp, String wind, String weather);
    }

    interface MainPresenter {
        void onMapWasClicked(String latitude, String longitude);
    }

    interface ListWeatherView{
//        void showTwoWeeksWeather(ArrayList<WeatherInfo> weatherInfo);
    }

    interface ListPresenter{
        void onActivityCreated(String latitude, String longitude);
    }



    interface Repository {
        String loadTwoWeeksWeather(String latitude, String longitude) throws ExecutionException, InterruptedException;
        String loadCurrentWeather(String latitude, String longitude) throws ExecutionException, InterruptedException;
    }
}
