package com.myapp.test.weathermap.presenter.model.CurrentWeather;


public class WeatherInfo {
    private Weather [] weather;
    private Main main;
    private Wind wind;
    private String cod;

    public WeatherInfo(Weather[] weather, Main main, Wind wind, String cod) {
        this.weather = weather;
        this.main = main;
        this.wind = wind;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
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
