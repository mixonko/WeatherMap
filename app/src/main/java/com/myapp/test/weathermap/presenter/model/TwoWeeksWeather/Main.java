package com.myapp.test.weathermap.presenter.model.TwoWeeksWeather;

public class Main {
    private double temp;
    private double humidity;

    public Main(double temp, double humidity) {
        this.temp = temp;
        this.humidity = humidity;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
