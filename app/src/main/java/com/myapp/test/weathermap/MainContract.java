package com.myapp.test.weathermap;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.myapp.test.weathermap.view.ExampleAdapter;

import java.util.concurrent.ExecutionException;

public interface MainContract  {

    interface View {
        void showCurrentWeather(String temp, String wind, String weather, BitmapDescriptor icon);
    }

    interface MainPresenter {
        void onMapWasClicked(String latitude, String longitude);
    }

    interface ListWeatherView{
         void showTwoWeeksWeather(ExampleAdapter exampleAdapter);
    }

    interface ListPresenter{
        void onActivityCreated(String latitude, String longitude);
    }



    interface Repository {
        String loadTwoWeeksWeather(String latitude, String longitude) throws ExecutionException, InterruptedException;
        String loadCurrentWeather(String latitude, String longitude) throws ExecutionException, InterruptedException;
    }
}
