package com.poc.fb.fb_poc.location;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import javax.inject.Inject;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Provides a Google-based locations observable.
 */

public class GoogleLocationProvider implements ILocationProvider {

    private static final String TAG = "GoogleLocationProvider";
    final LocationRequest locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setNumUpdates(5)
            .setInterval(100);

   private ReactiveLocationProvider locationProvider;

    @Inject
    public GoogleLocationProvider(ReactiveLocationProvider locationProvider){
        this.locationProvider = locationProvider;
    }

    @Override
    public Observable<Location> getGpsLocationsObservable(long minTime, float minDistance) {

//        Observable<Boolean> permissionsObservable = requestor.getPermissionsObservable();

        return locationProvider
                .checkLocationSettings(
                        new LocationSettingsRequest.Builder()
                                .addLocationRequest(locationRequest)
                                .setAlwaysShow(true)  //Refrence: http://stackoverflow.com/questions/29824408/google-play-services-locationservices-api-new-option-never
                                .build()
                )
                .doOnNext(new Action1<LocationSettingsResult>() {
                    @Override
                    public void call(LocationSettingsResult locationSettingsResult) {
                        Status status = locationSettingsResult.getStatus();
                        if (status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
//                            try {
                            Log.d(TAG, "status.startResolutionForResult(application, REQUEST_CHECK_SETTINGS);");
//                                status.startResolutionForResult(application, REQUEST_CHECK_SETTINGS);
//                            } catch (IntentSender.SendIntentException th) {
//                                Log.e("MainActivity", "Error opening settings activity.", th);
//                            }
                        }
                    }
                })
                .flatMap(new Func1<LocationSettingsResult, Observable<Location>>() {
                    @Override
                    public rx.Observable<Location> call(LocationSettingsResult locationSettingsResult) {
                        return locationProvider.getUpdatedLocation(locationRequest);
                    }
                });
    }

}
