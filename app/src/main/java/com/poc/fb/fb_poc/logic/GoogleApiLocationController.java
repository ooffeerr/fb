package com.poc.fb.fb_poc.logic;

import android.location.Address;
import android.location.Location;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.LocationRequest;
import com.poc.fb.fb_poc.location.GoogleLocationProvider;
import com.poc.fb.fb_poc.location.ILocationProvider;
import com.poc.fb.fb_poc.utils.AddressToStringFunc;

import java.util.List;

import javax.inject.Inject;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by oferschonberger on 25/02/17.
 */
public class GoogleApiLocationController {

    public static final int REQUEST_CHECK_SETTINGS = 0;
    private static final String TAG = "GoogleApiLocationController";
    private final ILocationProvider locationProvider;
    ReactiveLocationProvider reactiveLocationProvider;
//    private final Application application;
//    private final PermissionsRequester requestor;

    @Inject
    public GoogleApiLocationController(ReactiveLocationProvider reactiveLocationProvider, GoogleLocationProvider locationProvider) {
//        this.application = activity.getApplication();
        this.reactiveLocationProvider = reactiveLocationProvider;
        this.locationProvider = locationProvider;
//        requestor = new PermissionsRequester(application);
    }

    final LocationRequest locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setNumUpdates(5)
            .setInterval(100);

    public Observable<Location> getUpdatesObservable() {
        return locationProvider.getGpsLocationsObservable(0, 0);
//        Observable<Boolean> permissionsObservable = requestor.getPermissionsObservable();
//
//        return reactiveLocationProvider
//                .checkLocationSettings(
//                        new LocationSettingsRequest.Builder()
//                                .addLocationRequest(locationRequest)
//                                .setAlwaysShow(true)  //Refrence: http://stackoverflow.com/questions/29824408/google-play-services-locationservices-api-new-option-never
//                                .build()
//                )
//                .doOnNext(new Action1<LocationSettingsResult>() {
//                    @Override
//                    public void call(LocationSettingsResult locationSettingsResult) {
//                        Status status = locationSettingsResult.getStatus();
//                        if (status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
////                            try {
//                                Log.d(TAG, "status.startResolutionForResult(application, REQUEST_CHECK_SETTINGS);");
////                                status.startResolutionForResult(application, REQUEST_CHECK_SETTINGS);
////                            } catch (IntentSender.SendIntentException th) {
////                                Log.e("MainActivity", "Error opening settings activity.", th);
////                            }
//                        }
//                    }
//                })
//                .flatMap(new Func1<LocationSettingsResult, Observable<Location>>() {
//                    @Override
//                    public rx.Observable<Location> call(LocationSettingsResult locationSettingsResult) {
//                        return reactiveLocationProvider.getUpdatedLocation(locationRequest);
//                    }
//                });
    }

    public Observable<Location> getLastKnownLocation() {
        return reactiveLocationProvider.getLastKnownLocation();
    }

    public Observable<String> getAggressObservable() {
        return reactiveLocationProvider.getUpdatedLocation(locationRequest)
                .flatMap(new Func1<Location, rx.Observable<List<Address>>>() {
                    @Override
                    public rx.Observable<List<Address>> call(Location location) {
                        return reactiveLocationProvider.getReverseGeocodeObservable(location.getLatitude(), location.getLongitude(), 1);
                    }
                })
                .map(new Func1<List<Address>, Address>() {
                    @Override
                    public Address call(List<Address> addresses) {
                        return addresses != null && !addresses.isEmpty() ? addresses.get(0) : null;
                    }
                })
                .map(new AddressToStringFunc())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ActivityRecognitionResult> getDetectedActivity(int meters) {
        return reactiveLocationProvider.getDetectedActivity(meters);
    }
}
