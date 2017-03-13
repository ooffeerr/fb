package com.poc.fb.fb_poc.utils;

import android.location.Location;
import android.util.Log;

import rx.functions.Func1;

public class LocationToStringFunc implements Func1<Location, String> {
    private static final String TAG = "LocationToStringFunc";

    @Override
    public String call(Location location) {
        if (location != null) {
            String locationStr = location.getLatitude() + " " + location.getLongitude() + " (accuracy: " + location.getAccuracy() + ")";
            Log.d(TAG, "location = " + locationStr);
            return locationStr;
        }
        return "no location available";
    }
}
