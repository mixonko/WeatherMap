package com.myapp.test.weathermap.presenter.model.CurrentWeather;

public class Wind {
    private double speed;

    public Wind(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}