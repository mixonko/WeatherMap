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
import android.widget.Toast;

import com.myapp.test.weathermap.MainContract;
import com.myapp.test.weathermap.R;
import com.myapp.test.weathermap.presenter.ListPresenter;

public class ListWeatherActivity extends Activity implements MainContract.ListWeatherView {

    private String name = "";
    private RecyclerView recyclerView;
    private TextView title;
    private LinearLayoutManager linearLayoutManager;
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
        recyclerView = findViewById(R.id.list);
        title = findViewById(R.id.title);

        title.setText(name);

        listPresenter = new ListPresenter(this);
        listPresenter.onActivityCreated(latitude, longitude);

    }

    @Override
    public void showTwoWeeksWeather(ExampleAdapter exampleAdapter) {
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setAdapter(exampleAdapter);
        title.setText(name);
    }

}
