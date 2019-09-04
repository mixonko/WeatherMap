package com.myapp.test.weathermap.presenter;

import android.view.MotionEvent;
import android.view.View;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myapp.test.weathermap.contract.MainContract;
import com.myapp.test.weathermap.myApplicationApp.MyApplication;
import com.myapp.test.weathermap.entity.currentWeather.Weather;
import com.myapp.test.weathermap.entity.currentWeather.WeatherInfo;
import com.myapp.test.weathermap.repository.MainRepository;

public class MainPresenter implements MainContract.MainPresenter, MainContract.Repository.OnFinishedListener {

    private MainContract.View mView;
    private MainRepository mRepository;

    private WeatherInfo weatherInfo;
    private String temp;
    private String wind;
    private String weather;
    private String icon;

    public MainPresenter(MainContract.View mView) {
        this.mView = mView;
        this.mRepository = new MainRepository();

    }

    @Override
    public void onMapWasClicked() {
            mView.hideEditText();
            mView.hideKeyboard();
            mView.addMarker(mView.getLatLng());
            mRepository.loadCurrentWeather(String.valueOf(mView.getLatLng().latitude),
                    String.valueOf(mView.getLatLng().longitude), this);
    }

    @Override
    public void onButtonWasClicked() {
        mView.showEditText();
    }

    @Override
    public void onInfoWindowsWasClicked() {
        mView.startFiveDayWeatherActivity(mView.getName(), String.valueOf(mView.getLatLng().latitude),
                String.valueOf(mView.getLatLng().longitude));
    }

    @Override
    public void onEditorActionWasClicked(LatLng latLng) {
            mView.hideEditText();
            mView.addMarker(latLng);
            mRepository.loadCurrentWeather(String.valueOf(mView.getLatLng().latitude),
                    String.valueOf(mView.getLatLng().longitude), this);
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
    public void spinerWasSelected(String tileType) {
        mView.showTile(tileType);
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

    @Override
    public void onFinished(String result) {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        weatherInfo = gson.fromJson(result, WeatherInfo.class);
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
    public void onFailure(String error) {
        mView.showError(error);
    }

    @Override
    public void showNoConnection() {
        mView.showNoConnectionText();
    }
}
