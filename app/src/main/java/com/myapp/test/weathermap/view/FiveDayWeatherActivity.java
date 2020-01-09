package com.myapp.test.weathermap.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.test.weathermap.contract.MainContract;
import com.myapp.test.weathermap.myApplicationApp.MyApplication;
import com.myapp.test.weathermap.R;
import com.myapp.test.weathermap.presenter.ListPresenter;
import com.myapp.test.weathermap.entity.fiveDayWeather.WeatherInfo;
import com.myapp.test.weathermap.view.adapters.ExampleAdapter;

import java.util.ArrayList;

public class FiveDayWeatherActivity extends AppCompatActivity implements MainContract.ListWeatherView {

    private String name = "";
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private RecyclerView recyclerView4;
    private RecyclerView recyclerView5;
    private TextView title, date1, date2, date3, date4, date5;
    private View line1, line2, line3, line4, line5, line6;
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

        progressBar = findViewById(R.id.progressBar);

        date1 = findViewById(R.id.date1);
        date2 = findViewById(R.id.date2);
        date3 = findViewById(R.id.date3);
        date4 = findViewById(R.id.date4);
        date5 = findViewById(R.id.date5);

        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        line3 = findViewById(R.id.line3);
        line4 = findViewById(R.id.line4);
        line5 = findViewById(R.id.line5);
        line6 = findViewById(R.id.line6);

        title = findViewById(R.id.title);
        title.setText(name);

        listPresenter = new ListPresenter(this);
        listPresenter.onActivityCreated();

    }

    @Override
    public void showFiveDayWeather(ArrayList<WeatherInfo> day1, ArrayList<WeatherInfo> day2,
                                   ArrayList<WeatherInfo> day3, ArrayList<WeatherInfo> day4,
                                   ArrayList<WeatherInfo> day5) {


        date1.setText(day1.get(1).getDt_txt().substring(0, 10));
        recyclerView1.setLayoutManager(getLayoutManager());
        recyclerView1.setAdapter(getExampleAdapter(day1));

        date2.setText(day2.get(1).getDt_txt().substring(0, 10));
        recyclerView2.setLayoutManager(getLayoutManager());
        recyclerView2.setAdapter(getExampleAdapter(day2));

        date3.setText(day3.get(1).getDt_txt().substring(0, 10));
        recyclerView3.setLayoutManager(getLayoutManager());
        recyclerView3.setAdapter(getExampleAdapter(day3));

        date4.setText(day4.get(1).getDt_txt().substring(0, 10));
        recyclerView4.setLayoutManager(getLayoutManager());
        recyclerView4.setAdapter(getExampleAdapter(day4));

        date5.setText(day5.get(1).getDt_txt().substring(0, 10));
        recyclerView5.setLayoutManager(getLayoutManager());
        recyclerView5.setAdapter(getExampleAdapter(day5));

    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showLines() {
        line1.setVisibility(View.VISIBLE);
        line2.setVisibility(View.VISIBLE);
        line3.setVisibility(View.VISIBLE);
        line4.setVisibility(View.VISIBLE);
        line5.setVisibility(View.VISIBLE);
        line6.setVisibility(View.VISIBLE);
    }

    private LinearLayoutManager getLayoutManager() {
        return new LinearLayoutManager(MyApplication.getAppContext(), 0, false);
    }

    private ExampleAdapter getExampleAdapter(ArrayList<WeatherInfo> list) {
        return new ExampleAdapter(list);
    }


    @Override
    public String getLatitude() {
        return latitude;
    }

    @Override
    public String getLongitude() {
        return longitude;
    }

    @Override
    public void showNoConnection() {
        Toast.makeText(MyApplication.getAppContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(MyApplication.getAppContext(), error, Toast.LENGTH_LONG).show();
    }

}
