package com.myapp.test.weathermap.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.myapp.test.weathermap.MainContract;
import com.myapp.test.weathermap.MyApplication;
import com.myapp.test.weathermap.R;
import com.myapp.test.weathermap.presenter.ListPresenter;
import com.myapp.test.weathermap.presenter.model.fiveDayWeather.WeatherInfo;

import java.util.ArrayList;

public class ListWeatherActivity extends Activity implements MainContract.ListWeatherView {

    private String name = "";
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private RecyclerView recyclerView4;
    private RecyclerView recyclerView5;
    private RecyclerView recyclerView6;
    private TextView title;
    private MainContract.ListPresenter listPresenter;
    private String latitude;
    private String longitude;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_weather);

        Intent intent = getIntent();
        name = intent.getStringExtra(MapsActivity.NAME);
        latitude = intent.getStringExtra(MapsActivity.LATITUDE);
        longitude = intent.getStringExtra(MapsActivity.LONGITUDE);

        progressBar = findViewById(R.id.progressBar);
        recyclerView1 = findViewById(R.id.list1);
        recyclerView2 = findViewById(R.id.list2);
        recyclerView3 = findViewById(R.id.list3);
        recyclerView4 = findViewById(R.id.list4);
        recyclerView5 = findViewById(R.id.list5);
        recyclerView6 = findViewById(R.id.list6);
        title = findViewById(R.id.title);

        title.setText(name);

        listPresenter = new ListPresenter(this);
        listPresenter.onActivityCreated(latitude, longitude);

    }

    @Override
    public void showFiveDayWeather(Object[] days) {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView1.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext(), 1, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext(), 1, false));
        recyclerView3.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext(), 1, false));
        recyclerView4.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext(), 1, false));
        recyclerView5.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext(), 1, false));
        recyclerView6.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext(), 1, false));
        recyclerView1.setAdapter(new ExampleAdapter((ArrayList<WeatherInfo>) days[0]));
        recyclerView2.setAdapter(new ExampleAdapter((ArrayList<WeatherInfo>) days[1]));
        recyclerView3.setAdapter(new ExampleAdapter((ArrayList<WeatherInfo>) days[2]));
        recyclerView4.setAdapter(new ExampleAdapter((ArrayList<WeatherInfo>) days[3]));
        recyclerView5.setAdapter(new ExampleAdapter((ArrayList<WeatherInfo>) days[4]));
        recyclerView6.setAdapter(new ExampleAdapter((ArrayList<WeatherInfo>) days[5]));
        title.setText(name);
    }

}
