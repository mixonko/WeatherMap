package com.myapp.test.weathermap.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.myapp.test.weathermap.myApplicationApp.MyApplication;

public class SelfPermission {
    public static boolean checkSelfPermission(@NonNull Context context) {
        return ActivityCompat.checkSelfPermission(MyApplication.getAppContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MyApplication.getAppContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;
    }
}
