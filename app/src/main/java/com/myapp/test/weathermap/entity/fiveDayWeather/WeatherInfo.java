package com.myapp.test.weathermap.entity.fiveDayWeather;

public class WeatherInfo {
    private Main main;
    private long dt;
    private Weather[] weather;
    private Clouds clouds;
    private Wind wind;
    private String dt_txt;

    public WeatherInfo(Main main, long dt, Weather[] weather, Clouds clouds, Wind wind, String dt_txt) {
        this.main = main;
        this.dt = dt;
        this.weather = weather;
        this.clouds = clouds;
        this.wind = wind;
        this.dt_txt = dt_txt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }
}
