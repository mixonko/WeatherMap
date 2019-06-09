package com.myapp.test.weathermap.presenter.model.TwoWeeksWeather;

public class WeatherInfo {
    private Main main;
    private long dt;
    private Weather[] weather;
    private Clouds clouds;
    private Wind wind;

    public WeatherInfo(Main main, int dt_txt, Weather[] weather, Clouds clouds, Wind wind) {
        this.main = main;
        this.dt = dt_txt;
        this.weather = weather;
        this.clouds = clouds;
        this.wind = wind;
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
}
