package com.myapp.test.weathermap.presenter;

import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myapp.test.weathermap.MainContract;
import com.myapp.test.weathermap.MyApplication;
import com.myapp.test.weathermap.presenter.model.currentWeather.Weather;
import com.myapp.test.weathermap.presenter.model.currentWeather.WeatherInfo;
import com.myapp.test.weathermap.repository.MainRepository;

import java.io.IOException;

public class MainPresenter implements MainContract.MainPresenter {

    private MainContract.View mView;
    private MainRepository mRepository;

    private WeatherInfo weatherInfo;
    private String result;
    private String temp;
    private String wind;
    private String weather;
    private String icon;

    public MainPresenter(MainContract.View mView) {
        this.mView = mView;
        this.mRepository = new MainRepository();

    }

    @Override
    public void onMapWasClicked(LatLng latLng) {
        if (isOnline()) {
            mView.hideEditText();
            mView.hideKeyboard();
            mView.addMarker(latLng);
            getWeatherInfo(latLng);
        } else mView.showNoConnectionText();
    }

    private void getWeatherInfo(final LatLng latLng) {
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                result = mRepository.loadCurrentWeather(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude));
                return result;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                ShowWeather(String.valueOf(o));
            }
        }.execute();
    }

    private void ShowWeather(String res) {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        weatherInfo = gson.fromJson(res, WeatherInfo.class);

        if (Integer.parseInt(weatherInfo.getCod()) == 200) {
            temp = "Температура: " + getCelsius(weatherInfo.getMain().getTemp()) + " °C";
            wind = "Скорость ветра: " + String.valueOf(weatherInfo.getWind().getSpeed()) + " м/с";
            weather = getWeather(weatherInfo.getWeather());
            icon = getImage(weatherInfo.getWeather()[0].getIcon());
            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(MyApplication.getAppContext().getResources().getIdentifier(icon, "drawable", MyApplication.getAppContext().getPackageName()));
            mView.showCurrentWeather(temp, wind, weather, bitmap);
        } else mView.showCurrentWeather("Сервер", "не", "отвечает", null);

    }

    @Override
    public void onButtonWasClicked() {
        mView.showEditText();
    }

    @Override
    public void onInfoWindowsWasClicked() {
        mView.startFiveDayWeatherActivity();
    }

    @Override
    public void onEditorActionWasClicked(LatLng latLng) {
        mView.hideEditText();
        if (isOnline()) {
            mView.addMarker(latLng);
            getWeatherInfo(latLng);
        } else mView.showNoConnectionText();
    }

    @Override
    public void onEditTextDrawableWasClicked(View view, MotionEvent motionEvent) {
        mView.deleteText(view, motionEvent);
    }

    @Override
    public void onCameraMove() {
        mView.hideEditText();
        mView.hideKeyboard();
    }

    @Override
    public void noInformation() {
        mView.showNoInformation();
    }

    @Override
    public void spinerWasSelected() {
        mView.showTile();
    }

    private String getImage(String image) {
        String a = image.substring(image.length() - 1, image.length());
        String b = a + image.substring(0, image.length() - 1);
        return b;
    }

    private String getCelsius(Double kelvin) {
        String conversion = (String.valueOf(kelvin - 273.15));
        int position = conversion.indexOf(".");
        String celsius = conversion.substring(0, position + 2);
        return celsius;
    }

    private String getWeather(Weather[] wr) {
        return wr[0].getDescription();
    }

    protected boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }
}
