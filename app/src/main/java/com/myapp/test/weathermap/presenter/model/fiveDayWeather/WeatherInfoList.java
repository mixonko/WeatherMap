package com.myapp.test.weathermap.presenter.model.fiveDayWeather;


public class WeatherInfoList {
    private WeatherInfo[] list;
    private String cod;

    public WeatherInfoList(WeatherInfo[] list, String cod) {
        this.list = list;
        this.cod = cod;
    }

    public WeatherInfo[] getList() {
        return list;
    }

    public void setList(WeatherInfo[] list) {
        this.list = list;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }
}
