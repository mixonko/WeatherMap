package com.myapp.test.weathermap.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.myapp.test.weathermap.MainContract;
import com.myapp.test.weathermap.MyApplication;
import com.myapp.test.weathermap.R;
import com.myapp.test.weathermap.presenter.MainPresenter;
import com.myapp.test.weathermap.view.FiveDayWeatherActivity;


import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMapClickListener, GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener, MainContract.View{

    private GoogleMap mMap;
    private Marker marker;
    private MainContract.MainPresenter mPresenter;
    private TextView name, temperature, wind, weather;
    private EditText locationSearch;
    private Button locationSearchButton;
    private View view;
    private List<Address> addressList;
    private Geocoder geocoder;
    public static final String NAME = "name";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    private static final String WEATHER_MAP_URL_FORMAT =
            "https://tilecache.rainviewer.com/v2/radar/1560888000/512/%d/%d/%d.png?color=1";
    private LatLng latLng;
    private TileProvider tileProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        view = getLayoutInflater().inflate(R.layout.marker_info_windows, null);

        mPresenter = new MainPresenter(this);

        name = view.findViewById(R.id.name);
        temperature = view.findViewById(R.id.temperature);
        wind = view.findViewById(R.id.wind);
        weather = view.findViewById(R.id.weather);
        locationSearch = findViewById(R.id.location_search);
        locationSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {

                    String location = locationSearch.getText().toString();

                    if (!location.equals("")) {
                        try {
                            addressList = geocoder.getFromLocationName(location, 1);

                            Address address = addressList.get(0);
                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                            mPresenter.onEditorActionWasClicked(latLng);
                        } catch (Exception e) {
                            mPresenter.noInformation();
                        }
                    }
                    locationSearch.setText("");
                    return true;
                }
                return false;
            }
        });
        locationSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mPresenter.onEditTextDrawableWasClicked(view, motionEvent);
                return false;
            }
        });

        locationSearchButton = findViewById(R.id.locationSearchButton);
        locationSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onButtonWasClicked();
            }
        });

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
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                mPresenter.onCameraMove();
            }
        });

        geocoder = new Geocoder(MyApplication.getAppContext(), Locale.getDefault());

        if (ActivityCompat.checkSelfPermission(MyApplication.getAppContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MyApplication.getAppContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
        mMap.setMyLocationEnabled(true);

//        tileProvider = new UrlTileProvider(512, 512) {
//            @Override
//            public URL getTileUrl(int x, int y, int zoom) {
//                String s = String.format(WEATHER_MAP_URL_FORMAT, zoom, x, y);
//                URL url = null;
//                try {
//                    url = new URL(s);
//                } catch (MalformedURLException e) {
//                    throw new AssertionError(e);
//                }
//
//                return url;
//            }
//        };
//
//        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(tileProvider));
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMapClick(final LatLng latLng) {
        this.latLng = latLng;
        mPresenter.onMapWasClicked(latLng);
        marker = mMap.addMarker(new MarkerOptions()
                .position(latLng));
    }

    @Override
    public void showCurrentWeather(String getTemp, String getWind, String getWeather, BitmapDescriptor icon) {

        try {
            addressList = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
            name.setText(String.valueOf(addressList.get(0).getAddressLine(0)));
            temperature.setText(getTemp);
            wind.setText(getWind);
            weather.setText(getWeather);
            marker.setIcon(icon);
            marker.showInfoWindow();
            locationSearch.setText(String.valueOf(addressList.get(0).getAddressLine(0)));
        } catch (Exception e) {
            mPresenter.noInformation();
        }

    }

    @Override
    public void showEditText() {
        locationSearchButton.setVisibility(View.INVISIBLE);
        locationSearch.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEditText() {
        locationSearchButton.setVisibility(View.VISIBLE);
        locationSearch.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showNoConnectionText() {
        Toast.makeText(MyApplication.getAppContext(), "Отсутствует интернет соединение", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showNoInformation() {
        locationSearch.setText("");
        Toast.makeText(MyApplication.getAppContext(), "Информация не найдена", Toast.LENGTH_LONG).show();
    }

    @Override
    public void startFiveDayWeatherActivity() {
        Intent intent = new Intent(MyApplication.getAppContext(), FiveDayWeatherActivity.class);
        intent.putExtra(NAME, name.getText().toString());
        String latitude = String.valueOf(latLng.latitude);
        String longitude = String.valueOf(latLng.longitude);
        intent.putExtra(LATITUDE, latitude);
        intent.putExtra(LONGITUDE, longitude);
        startActivity(intent);
    }

    @Override
    public void deleteText(View view, MotionEvent motionEvent) {
        final int DRAWABLE_RIGHT = 2;
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if (motionEvent.getRawX() >= (locationSearch.getRight() - locationSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                locationSearch.setText("");
            }
        }
    }

    @Override
    public void animateCamera(final LatLng latLng) {
        try {
            marker.remove();
        } catch (Exception e) {
        }

        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng), 500, null);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        mPresenter.onInfoWindowsWasClicked();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return  view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

}
