package com.myapp.test.weathermap.entity.currentWeather;


public class WeatherInfo {
    private Weather[] weather;
    private Main main;
    private Wind wind;
    private String cod;

    public WeatherInfo(com.myapp.test.weathermap.entity.currentWeather.Weather[] weather, com.myapp.test.weathermap.entity.currentWeather.Main main, com.myapp.test.weathermap.entity.currentWeather.Wind wind, String cod) {
        this.weather = weather;
        this.main = main;
        this.wind = wind;
    }

    public com.myapp.test.weathermap.entity.currentWeather.Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public com.myapp.test.weathermap.entity.currentWeather.Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public com.myapp.test.weathermap.entity.currentWeather.Wind getWind() {
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
