package com.myapp.test.weathermap.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myapp.test.weathermap.MainContract;
import com.myapp.test.weathermap.MyApplication;
import com.myapp.test.weathermap.R;
import com.myapp.test.weathermap.presenter.model.CurrentWeather.Weather;
import com.myapp.test.weathermap.presenter.model.CurrentWeather.WeatherInfo;
import com.myapp.test.weathermap.repository.MainRepository;


public class MainPresenter implements MainContract.MainPresenter {

    private MainContract.View mView;
    private MainRepository mRepository;

    private String result;

    private WeatherInfo weatherInfo;

    private String temp;
    private String wind;
    private String weather;


    public MainPresenter(MainContract.View mView) {
        this.mView = mView;
        this.mRepository = new MainRepository();

    }

    @Override
    public void onMapWasClicked(final String latitude, final String longitude) {

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                result = mRepository.loadCurrentWeather(latitude, longitude);
                return result;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                View(String.valueOf(o));
            }
        }.execute();

    }

    private String getCelsius(Double kelvin) {
        String conversion = (String.valueOf(kelvin - 273.15));
        int position = conversion.indexOf(".");
        String celsius = conversion.substring(0, position + 2);
        return celsius;
    }

    private String getWeather(Weather[] wr) {
        wr = weatherInfo.getWeather();
        return wr[0].getDescription();
    }

    private void View(String res) {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        weatherInfo = gson.fromJson(res, WeatherInfo.class);

        if (Integer.parseInt(weatherInfo.getCod()) == 200) {

            temp = "Температура: " + getCelsius(weatherInfo.getMain().getTemp()) + " °C";
            wind = "Скорость ветра " + String.valueOf(weatherInfo.getWind().getSpeed()) + " м/с";
            weather = getWeather(weatherInfo.getWeather());
            mView.showCurrentWeather(temp, wind, weather);


//            Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getAppContext().getResources(),
//                    R.drawable.common_full_open_on_phone);
//
//            Drawable d = new BitmapDrawable(getResources(), bitmap);

        } else mView.showCurrentWeather("Сервер", "не", "отвечает");

    }
}
