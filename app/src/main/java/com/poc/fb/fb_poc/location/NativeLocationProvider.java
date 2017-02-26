package com.poc.fb.fb_poc.location;

import android.content.Context;
import android.database.Observable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import rx.Subscriber;

/**
 * Created by oferschonberger on 26/02/17.
 */

public class NativeLocationProvider {


    public rx.Observable<Location> getLocationsObservable(Context context) {

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        rx.Observable<Location> locationObservable = rx.Observable.create(new rx.Observable.OnSubscribe<Location>() {

            @Override
            public void call(final Subscriber<? super Location> subscriber) {
                // Define a listener that responds to location updates
                LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        // Called when a new location is found by the network location provider.
                        subscriber.onNext(location);
                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {}

                    public void onProviderEnabled(String provider) {
                        subscriber.onStart();
                    }

                    public void onProviderDisabled(String provider) {
                        subscriber.onCompleted();
                    }
                };

// Register the listener with the Location Manager to receive location updates
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        });

        return locationObservable;
    }
}
