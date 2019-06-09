package com.myapp.test.weathermap.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.myapp.test.weathermap.MainContract;
import com.myapp.test.weathermap.R;
import com.myapp.test.weathermap.presenter.MainPresenter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMapClickListener, GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener, MainContract.View,
        View.OnClickListener {

    private GoogleMap mMap;
    private MarkerOptions marker;
    private MainContract.MainPresenter mPresenter;
    private TextView name, temperature, wind, weather;
    private EditText locationSearch;
    private Button locationSearchButton;
    private View view;
    private Address address;
    public static final String NAME = "name";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        marker = new MarkerOptions();

        view = getLayoutInflater().inflate(R.layout.marker_info_windows, null);

        name = view.findViewById(R.id.name);
        temperature = view.findViewById(R.id.temperature);
        wind = view.findViewById(R.id.wind);
        weather = view.findViewById(R.id.weather);
        locationSearch = findViewById(R.id.location_search);
        locationSearchButton = findViewById(R.id.locationSearchButton);

        locationSearchButton.setOnClickListener(this);


        mPresenter = new MainPresenter(this);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setInfoWindowAdapter(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMapClickListener(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);


    }

    @Override
    public boolean onMyLocationButtonClick() {

        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {

        mapWasClicked(latLng);
    }


    @Override
    public void onClick(View view) {
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(MapsActivity.this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mapWasClicked(latLng);
        }
    }

    private void mapWasClicked(LatLng latLng) {
        mMap.clear();
        this.latLng = latLng;

        if (true) {
            marker.position(latLng);
            mMap.addMarker(marker).showInfoWindow();
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            Geocoder gcd = new Geocoder(this, Locale.getDefault());
            List<Address> list = new ArrayList<>();
            try {
                list = gcd.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            address = list.get(0);
            mPresenter.onMapWasClicked(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude));


        } else {
            Toast.makeText(this, "Отсутствувет интернет соединение", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void showCurrentWeather(String getTemp, String getWind, String getWeather) {
        name.setText(address.getAddressLine(0));
        temperature.setText(getTemp);
        wind.setText(getWind);
        weather.setText(getWeather);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return view;
    }

    protected boolean isOnline() {
        try {
            HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.anywebsiteyouthinkwillnotbedown.com").openConnection());
            urlc.setRequestProperty("User-Agent", "Test");
            urlc.setRequestProperty("Connection", "close");
            urlc.setConnectTimeout(3000);
            urlc.setReadTimeout(4000);
            urlc.connect();
            int networkcode2 = urlc.getResponseCode();
            return (urlc.getResponseCode() == 200);
        } catch (IOException e) {
            return (false);
        }
    }

}
