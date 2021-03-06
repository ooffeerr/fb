package com.poc.fb.fb_poc.logic;

import android.location.Location;
import android.util.Log;

import com.poc.fb.fb_poc.db.IDatabase;
import com.poc.fb.fb_poc.db.LocationDatabase;
import com.poc.fb.fb_poc.location.ILocationUpdateUIListener;
import com.poc.fb.fb_poc.location.NativeLocationProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * General location related logic
 */
public class LocationController implements ILocationController {

    private static final String TAG = "LocationController";
    private Subscription subscribe;

    @Inject
    public LocationController(IDatabase locationDatabase, NativeLocationProvider nativeLocationProvider) {
        Log.d(TAG, "LocationController() called with: locationDatabase = [" + locationDatabase + "], nativeLocationProvider = [" + nativeLocationProvider + "]");
        this.database = locationDatabase;
        this.nativeLocationProvider = nativeLocationProvider;
    }

    IDatabase database;

    NativeLocationProvider nativeLocationProvider;

    @Override
    public void setListener(ILocationUpdateUIListener listener) {
        this.listener = listener;
    }

    ILocationUpdateUIListener listener;
    Subscriber<Location> sub = new Subscriber<Location>() {
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
            Log.d(TAG, "onNext() called with: location = [" + location + "] + subscribe.isUnsubscribed() = " + subscribe.isUnsubscribed());
            if (subscribe.isUnsubscribed()) {
                onCompleted();
                return;
            }
//                database.addLocation(location);
            if (listener != null) {
                listener.updateDisplayWithLocation(location);
            }
        }
    };

    @Override
    public void startTrackingLocations() {
        Log.d(TAG, "startTrackingLocations() called");

        Observable<Location> gpsLocationsObservable = nativeLocationProvider.getGpsLocationsObservable(TimeUnit.SECONDS.toMillis(1), 0);
        subscribe = gpsLocationsObservable.subscribe(sub);
    }

    @Override
    public void stopTrackingLocations() {
        Log.d(TAG, "stopTrackingLocations() called, subscribe = " + subscribe + " sub = " + sub);
        if (subscribe != null) {
            subscribe.unsubscribe();
        }

        if (sub != null) {
            sub.unsubscribe();
        }
    }
}
