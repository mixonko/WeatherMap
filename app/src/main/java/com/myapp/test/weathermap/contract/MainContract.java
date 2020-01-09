package com.myapp.test.weathermap.contract;


import android.view.MotionEvent;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.myapp.test.weathermap.entity.fiveDayWeather.WeatherInfo;

import java.util.ArrayList;

public interface MainContract  {

    interface View {
        void showCurrentWeather(String temp, String wind, String weather, BitmapDescriptor icon);
        void addMarker(LatLng latLng);
        void showEditText();
        void hideEditText();
        void hideKeyboard();
        void showNoConnectionText();
        void deleteText(android.view.View view, MotionEvent motionEvent);
        void showNoInformation();
        void startFiveDayWeatherActivity(String name, String latitude, String longitude);
        void showTile(String tileType);
        void showError(String error);
        LatLng getLatLng();
        String getName();
    }

    interface MainPresenter {
        void onMapWasClicked ();
        void onButtonWasClicked();
        void onInfoWindowsWasClicked();
        void onEditorActionWasClicked(LatLng latLng);
        void onEditTextDrawableWasClicked(android.view.View view, MotionEvent motionEvent);
        void onCameraMove();
        void noInformation();
        void spinerWasSelected(String tileType);
    }

    interface ListWeatherView{
         void showFiveDayWeather(ArrayList<WeatherInfo> day1, ArrayList<WeatherInfo> day2,
                                 ArrayList<WeatherInfo> day3, ArrayList<WeatherInfo> day4,
                                 ArrayList<WeatherInfo> day5);
         void hideProgressBar();
         void showLines();
        String getLatitude();
        String getLongitude();
        void showNoConnection();
        void showError(String error);
    }

    interface ListPresenter{
        void onActivityCreated();
    }

    interface Repository {
        void loadFiveDayWeather(String latitude, String longitude, OnFinishedListener onFinishedListener);
        void loadCurrentWeather(String latitude, String longitude, OnFinishedListener onFinishedListener);

        interface OnFinishedListener{
            void onFinished(String result);
            void onFailure(String error);
            void showNoConnection();
        }
    }
}
