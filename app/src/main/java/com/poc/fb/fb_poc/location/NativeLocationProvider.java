package com.poc.fb.fb_poc.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Creates a native Android locations observable for locations consumption.
 */

public class NativeLocationProvider {

    public static final String TAG = "LocationObservable";

    @Inject private LocationManager locationManager;

    public rx.Observable<Location> getGpsLocationsObservable(Context context, final long minTime, final float minDistance) {

//        // Acquire a reference to the system Location Manager
//        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        rx.Observable<Location> locationObservable = rx.Observable.create(new rx.Observable.OnSubscribe<Location>() {

            @Override
            public void call(final Subscriber<? super Location> subscriber) {
                // Define a listener that responds to location updates
                final LocationListener locationListener = new LocationListener() {

                    public void onLocationChanged(Location location) {
                        Log.d(TAG, "onLocationChanged() called with: location = [" + location + "]");
                        // Called when a new location is found by the network location provider.
                        subscriber.onNext(location);
                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {
                        Log.d(TAG, "onStatusChanged() called with: provider = [" + provider + "], status = [" + status + "], extras = [" + extras + "]");
                    }

                    public void onProviderEnabled(String provider) {
                        Log.d(TAG, "onProviderEnabled() called with: provider = [" + provider + "]");
                        subscriber.onStart();
                    }

                    public void onProviderDisabled(String provider) {
                        Log.d(TAG, "onProviderDisabled() called with: provider = [" + provider + "]");
                        subscriber.onCompleted();
                    }
                };
                Log.d(TAG, "requestLocationUpdates");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, locationListener);
            }
        });

        return locationObservable;
    }
}
