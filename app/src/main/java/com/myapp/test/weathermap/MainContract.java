package com.myapp.test.weathermap;


import android.view.MotionEvent;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.myapp.test.weathermap.presenter.model.fiveDayWeather.WeatherInfo;

import java.util.ArrayList;

public interface MainContract  {

    interface View {
        void showCurrentWeather(String temp, String wind, String weather, BitmapDescriptor icon);
        void animateCamera(LatLng latLng);
        void showEditText();
        void hideEditText();
        void showNoConnectionText();
        void deleteText(android.view.View view, MotionEvent motionEvent);
        void showNoInformation();
        void startFiveDayWeatherActivity();

    }

    interface MainPresenter {
        void onMapWasClicked (LatLng latLng);
        void onButtonWasClicked();
        void onInfoWindowsWasClicked();
        void onEditorActionWasClicked(LatLng latLng);
        void onEditTextDrawableWasClicked(android.view.View view, MotionEvent motionEvent);
        void onCameraMove();
        void noInformation();
    }

    interface ListWeatherView{
         void showFiveDayWeather(ArrayList<WeatherInfo> day1, ArrayList<WeatherInfo> day2,
                                 ArrayList<WeatherInfo> day3, ArrayList<WeatherInfo> day4,
                                 ArrayList<WeatherInfo> day5, ArrayList<WeatherInfo> day6);
    }

    interface ListPresenter{
        void onActivityCreated(String latitude, String longitude);
    }



    interface Repository {
        String loadFiveDayWeather(String latitude, String longitude);
        String loadCurrentWeather(String latitude, String longitude);
        String loadTimeZone();
        String loadLayer(String timeZone, String zoom, String x, String y);
    }
}
