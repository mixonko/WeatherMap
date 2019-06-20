package com.myapp.test.weathermap;


import android.view.MotionEvent;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.myapp.test.weathermap.view.ExampleAdapter;

public interface MainContract  {

    interface View {
        void showCurrentWeather(String temp, String wind, String weather, BitmapDescriptor icon);
        void animateCamera(LatLng latLng);
        void showEditText();
        void hideEditText();
        void showNoConnectionText();
        void deleteText(android.view.View view, MotionEvent motionEvent);
        void showNoInformation();

    }

    interface MainPresenter {
        void onMapWasClicked (LatLng latLng);
        void onButtonWasClicked();
        void onEditorActionWasClicked(LatLng latLng);
        void onEditTextDrawableWasClicked(android.view.View view, MotionEvent motionEvent);
        void onCameraMove();
        void noInformation();
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
