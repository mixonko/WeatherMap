package com.myapp.test.weathermap.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.myapp.test.weathermap.R;
import com.myapp.test.weathermap.presenter.model.fiveDayWeather.WeatherInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private ArrayList<WeatherInfo> weatherInfoArrayList;
    private WeatherInfo weatherInfo;

    public ExampleAdapter(ArrayList<WeatherInfo> weatherInfoArrayList) {
        this.weatherInfoArrayList = weatherInfoArrayList;
    }


    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView temp;
        public TextView weather;
        public TextView clouds;
        public TextView humidity;
        public TextView wind;
        public Spinner spinner;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            temp = itemView.findViewById(R.id.temp);
            weather = itemView.findViewById(R.id.weather);
            clouds = itemView.findViewById(R.id.clouds);
            humidity = itemView.findViewById(R.id.humidity);
            wind = itemView.findViewById(R.id.wind);
        }
    }


    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.example_item, viewGroup, false);
        ExampleViewHolder exampleViewHolder = new ExampleViewHolder(v);
        return exampleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder, int i) {
        weatherInfo = weatherInfoArrayList.get(i);

        exampleViewHolder.date.setText(getDate(weatherInfo.getDt()));
        exampleViewHolder.temp.setText(String.valueOf(getCelsius(weatherInfo.getMain().getTemp())) + " °C");
        exampleViewHolder.weather.setText(weatherInfo.getWeather()[0].getDescription());
        exampleViewHolder.clouds.setText(String.valueOf(weatherInfo.getClouds().getAll()) + " %");
        exampleViewHolder.humidity.setText(String.valueOf(weatherInfo.getMain().getHumidity()) + " %");
        exampleViewHolder.wind.setText(String.valueOf(weatherInfo.getWind().getSpeed()) + " м/c");

    }

    private String getDate(long dt) {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date
                (dt * 1000));

    }

    private String getCelsius(Double kelvin) {
        String conversion = (String.valueOf(kelvin - 273.15));
        int position = conversion.indexOf(".");
        String celsius = conversion.substring(0, position + 2);
        return celsius;
    }

    @Override
    public int getItemCount() {
        return weatherInfoArrayList.size();
    }

}
