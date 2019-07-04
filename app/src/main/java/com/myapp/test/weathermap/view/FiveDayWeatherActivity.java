package com.myapp.test.weathermap.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.myapp.test.weathermap.view.adapters.ExampleAdapter;

import java.util.ArrayList;

public class FiveDayWeatherActivity extends AppCompatActivity implements MainContract.ListWeatherView {

    private String name = "";
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private RecyclerView recyclerView4;
    private RecyclerView recyclerView5;
    private RecyclerView recyclerView6;
    private TextView title, date1, date2, date3, date4, date5, date6;
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

        recyclerView1 = findViewById(R.id.list1);
        recyclerView2 = findViewById(R.id.list2);
        recyclerView3 = findViewById(R.id.list3);
        recyclerView4 = findViewById(R.id.list4);
        recyclerView5 = findViewById(R.id.list5);
        recyclerView6 = findViewById(R.id.list6);

        progressBar = findViewById(R.id.progressBar);

        date1 = findViewById(R.id.date1);
        date2 = findViewById(R.id.date2);
        date3 = findViewById(R.id.date3);
        date4 = findViewById(R.id.date4);
        date5 = findViewById(R.id.date5);
        date6 = findViewById(R.id.date6);

        title = findViewById(R.id.title);
        title.setText(name);

        listPresenter = new ListPresenter(this);
        listPresenter.onActivityCreated(latitude, longitude);

    }

    @Override
    public void showFiveDayWeather(ArrayList<WeatherInfo> day1, ArrayList<WeatherInfo> day2,
                                   ArrayList<WeatherInfo> day3, ArrayList<WeatherInfo> day4,
                                   ArrayList<WeatherInfo> day5, ArrayList<WeatherInfo> day6) {

        progressBar.setVisibility(View.INVISIBLE);


        date1.setText(day1.get(1).getDt_txt().substring(0, 10));
        recyclerView1.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext(), 0, false));
        recyclerView1.setAdapter(new ExampleAdapter(day1));

        date2.setText(day1.get(1).getDt_txt().substring(0, 10));
        recyclerView2.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext(), 0, false));
        recyclerView2.setAdapter(new ExampleAdapter(day2));

        date3.setText(day1.get(1).getDt_txt().substring(0, 10));
        recyclerView3.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext(), 0, false));
        recyclerView3.setAdapter(new ExampleAdapter(day3));

        date4.setText(day1.get(1).getDt_txt().substring(0, 10));
        recyclerView4.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext(), 0, false));
        recyclerView4.setAdapter(new ExampleAdapter(day4));

        date5.setText(day1.get(1).getDt_txt().substring(0, 10));
        recyclerView5.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext(), 0, false));
        recyclerView5.setAdapter(new ExampleAdapter(day5));

        if(day6 != null){
            date6.setText(day1.get(1).getDt_txt().substring(0, 10));
            recyclerView6.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext(), 0, false));
            recyclerView6.setAdapter(new ExampleAdapter(day6));
        }

    }

}
