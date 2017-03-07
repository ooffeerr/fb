package com.poc.fb.fb_poc.logic;

import android.location.Location;
import android.util.Log;

import com.poc.fb.fb_poc.db.LocationDatabase;
import com.poc.fb.fb_poc.location.NativeLocationProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * General location related logic
 */
public class LocationController implements ILocationController {


    private static final String TAG = "LocationController";

    @Inject
    public LocationController(LocationDatabase locationDatabase, NativeLocationProvider nativeLocationProvider) {
        Log.d(TAG, "LocationController() called with: locationDatabase = [" + locationDatabase + "], nativeLocationProvider = [" + nativeLocationProvider + "]");
        this.database = locationDatabase;
        this.nativeLocationProvider = nativeLocationProvider;
    }

    @Inject
    LocationDatabase database;

    @Inject
    NativeLocationProvider nativeLocationProvider;

    @Override
    public void startTrackingLocations() {
        Log.d(TAG, "startTrackingLocations() called");

        Observable<Location> gpsLocationsObservable = nativeLocationProvider.getGpsLocationsObservable(TimeUnit.SECONDS.toMillis(1), 0);
        gpsLocationsObservable.subscribe(new Subscriber<Location>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted() called");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError() called with: e = [" + e + "]");
            }

            @Override
            public void onNext(Location location) {
                Log.d(TAG, "onNext() called with: location = [" + location + "]");
                database.addLocation(location);
            }
        });
    }
}
