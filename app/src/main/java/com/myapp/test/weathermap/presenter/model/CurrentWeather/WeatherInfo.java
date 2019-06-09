package com.myapp.test.weathermap.presenter.model.CurrentWeather;


public class WeatherInfo {
    private com.myapp.test.weathermap.presenter.model.CurrentWeather.Weather[] weather;
    private com.myapp.test.weathermap.presenter.model.CurrentWeather.Main main;
    private com.myapp.test.weathermap.presenter.model.CurrentWeather.Wind wind;
    private String cod;

    public WeatherInfo(com.myapp.test.weathermap.presenter.model.CurrentWeather.Weather[] weather, com.myapp.test.weathermap.presenter.model.CurrentWeather.Main main, com.myapp.test.weathermap.presenter.model.CurrentWeather.Wind wind, String cod) {
        this.weather = weather;
        this.main = main;
        this.wind = wind;
    }

    public com.myapp.test.weathermap.presenter.model.CurrentWeather.Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public com.myapp.test.weathermap.presenter.model.CurrentWeather.Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public com.myapp.test.weathermap.presenter.model.CurrentWeather.Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }
}
