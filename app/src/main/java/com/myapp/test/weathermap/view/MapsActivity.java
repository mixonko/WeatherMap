package com.myapp.test.weathermap.view;

import android.Manifest;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;
import com.myapp.test.weathermap.contract.MainContract;
import com.myapp.test.weathermap.myApplicationApp.MyApplication;
import com.myapp.test.weathermap.R;
import com.myapp.test.weathermap.presenter.MainPresenter;
import com.myapp.test.weathermap.utils.InternetConnection;
import com.myapp.test.weathermap.utils.SelfPermission;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMapClickListener, GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener, MainContract.View,
        GoogleMap.OnMarkerClickListener, AdapterView.OnItemSelectedListener {

    private GoogleMap mMap;
    private Marker marker;
    private MainContract.MainPresenter mPresenter;
    private TextView name, temperature, wind, weather;
    private EditText locationSearch;
    private Button locationSearchButton;
    private View view;
    private List<Address> addressList;
    private Geocoder geocoder;
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String NAME = "name";
    private LatLng mlatLng;
    private TileOverlay tileOverlay;
    private Spinner spinner;
    private String tileType = null;
    private Boolean showHideInfoWindow = true;

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

        spinner = findViewById(R.id.tileType);
        String[] tileName = getResources().getStringArray(R.array.list);
        ArrayAdapter<String> adpt = new ArrayAdapter(MyApplication.getAppContext(), android.R.layout.simple_list_item_activated_1, tileName);
        spinner.setAdapter(adpt);
        spinner.setOnItemSelectedListener(this);

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
                            mPresenter.onEditorActionWasClicked(new LatLng(address.getLatitude(),
                                    address.getLongitude()));
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
        mMap.setOnMarkerClickListener(this);
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                mPresenter.onCameraMove();
            }
        });

        geocoder = new Geocoder(MyApplication.getAppContext(), Locale.getDefault());

        if (SelfPermission.checkSelfPermission(MyApplication.getAppContext())) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
        mMap.setMyLocationEnabled(true);

    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMapClick(final LatLng latLng) {
        this.mlatLng = latLng;
        mPresenter.onMapWasClicked();
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
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) locationSearch.getContext().getSystemService(MyApplication.getAppContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(locationSearch.getWindowToken(), 0);

    }

    @Override
    public void showNoConnectionText() {
        Toast.makeText(MyApplication.getAppContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showNoInformation() {
        locationSearch.setText("");
        Toast.makeText(MyApplication.getAppContext(), R.string.no_information, Toast.LENGTH_LONG).show();
    }

    @Override
    public void startFiveDayWeatherActivity(String name, String latitude, String longitude) {
        Intent intent = new Intent(MyApplication.getAppContext(), FiveDayWeatherActivity.class);
        intent.putExtra(NAME, name);
        intent.putExtra(LATITUDE, latitude);
        intent.putExtra(LONGITUDE, longitude);
        startActivity(intent);
    }

    @Override
    public void showTile(String tileType) {
        final String WEATHER_MAP_URL_FORMAT = "https://tile.openweathermap.org/map/" + tileType + "/%d/%d/%d.png?appid=db2c05a113d582c380b53504bc58b1fd";

        TileProvider tileProvider = new UrlTileProvider(256, 256) {
            @Override
            public URL getTileUrl(int x, int y, int zoom) {
                String fUrl = String.format(WEATHER_MAP_URL_FORMAT, zoom, x, y);
                URL url = null;
                try {
                    url = new URL(fUrl);
                } catch (MalformedURLException mfe) {
                    mfe.printStackTrace();
                }

                return url;
            }
        };
        tileOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(tileProvider));
    }

    @Override
    public void showError(String error) {
        Toast.makeText(MyApplication.getAppContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public LatLng getLatLng() {
        return mlatLng;
    }

    @Override
    public String getName() {
        return name.getText().toString();
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
    public void addMarker(final LatLng latLng) {
        try {
            marker.remove();
        } catch (Exception e) {
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng), 300, null);

        marker = mMap.addMarker(new MarkerOptions()
                .position(latLng));

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        mPresenter.onInfoWindowsWasClicked();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (showHideInfoWindow) {
            marker.hideInfoWindow();
            showHideInfoWindow = false;
            return true;
        }
        if (!showHideInfoWindow) {
            marker.showInfoWindow();
            showHideInfoWindow = true;
            return true;
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (InternetConnection.checkConnection(MyApplication.getAppContext())){
            if (tileOverlay != null)
                tileOverlay.remove();
            switch (i) {
                case 0:
                    tileType = null;
                    break;
                case 1:
                    tileType = "precipitation_new";
                    break;
                case 2:
                    tileType = "clouds_new";
                    break;
                case 3:
                    tileType = "temp_new";
                    break;
                case 4:
                    tileType = "wind_new";
                    break;
                case 5:
                    tileType = "pressure_new";
                    break;
            }
            if (tileType != null) mPresenter.spinerWasSelected(tileType);
        }else{
            spinner.setSelection(0);
            showNoConnectionText();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
