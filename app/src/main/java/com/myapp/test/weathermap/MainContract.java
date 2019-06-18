package com.myapp.test.weathermap;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.myapp.test.weathermap.view.ExampleAdapter;

public interface MainContract  {

    interface View {
        void showCurrentWeather(String temp, String wind, String weather, BitmapDescriptor icon);
        void addLayer();
    }

    interface MainPresenter {
        void onMapWasClicked(String latitude, String longitude);
        void onMenuWasClicked(String timeZone, String zoom, String x, String y);
    }

    interface ListWeatherView{
         void showTwoWeeksWeather(ExampleAdapter exampleAdapter);
    }

    interface ListPresenter{
        void onActivityCreated(String latitude, String longitude);
    }



    interface Repository {
        String loadTwoWeeksWeather(String latitude, String longitude);
        String loadCurrentWeather(String latitude, String longitude);
        String loadTimeZone();
        String loadLayer(String timeZone, String zoom, String x, String y);
    }
}
