package com.poc.fb.fb_poc.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Observable;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.poc.fb.fb_poc.R;
import com.poc.fb.fb_poc.logic.LocationController;
import com.poc.fb.fb_poc.utils.AddressToStringFunc;
import com.poc.fb.fb_poc.utils.DetectedActivityToString;
import com.poc.fb.fb_poc.utils.DisplayTextOnViewAction;
import com.poc.fb.fb_poc.utils.LocationToStringFunc;
import com.poc.fb.fb_poc.utils.ToMostProbableActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.poc.fb.fb_poc.logic.LocationController.REQUEST_CHECK_SETTINGS;
import static com.poc.fb.fb_poc.utils.UnsubscribeIfPresent.unsubscribe;

/**
 * Created by oferschonberger on 25/02/17.
 */
public class LocationFragment extends Fragment{

    private final static String TAG = "MainActivity";
    private ReactiveLocationProvider locationProvider;

    @BindView(R.id.last_known_location_view)  TextView lastKnownLocationView;
    @BindView(R.id.updated_location_view)  TextView updatableLocationView;
    @BindView(R.id.address_for_location_view)  TextView addressLocationView;
    @BindView(R.id.activity_recent_view) TextView currentActivityView;

    private rx.Observable<Location> lastKnownLocationObservable;
    private rx.Observable<Location> locationUpdatesObservable;
    private rx.Observable<ActivityRecognitionResult> activityObservable;

    private Subscription lastKnownLocationSubscription;
    private Subscription updatableLocationSubscription;
    private Subscription addressSubscription;
    private Subscription activitySubscription;
    private rx.Observable<String> addressObservable;
    private LocationController locationController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.location_fragment, null);
        ButterKnife.bind(this, view);

        locationController = new LocationController(getActivity());
        lastKnownLocationObservable = locationController.getLastKnownLocation();

        locationUpdatesObservable = locationController.getUpdatesObservable();
        addressObservable = locationController.getAggressObservable();

        activityObservable = locationController.getDetectedActivity(50);
        return view;
    }

//    @Override
//    public void onViewCreated(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//    }

//    @Override
    protected void onLocationPermissionGranted() {
        lastKnownLocationSubscription = lastKnownLocationObservable
                .map(new LocationToStringFunc())
                .subscribe(new DisplayTextOnViewAction(lastKnownLocationView), new ErrorHandler());

        updatableLocationSubscription = locationUpdatesObservable
                .map(new LocationToStringFunc())
                .map(new Func1<String, String>() {
                    int count = 0;

                    @Override
                    public String call(String s) {
                        return s + " " + count++;
                    }
                })
                .subscribe(new DisplayTextOnViewAction(updatableLocationView), new ErrorHandler());


        addressSubscription = addressObservable
                .subscribe(new DisplayTextOnViewAction(addressLocationView), new ErrorHandler());

        activitySubscription = activityObservable
                .map(new ToMostProbableActivity())
                .map(new DetectedActivityToString())
                .subscribe(new DisplayTextOnViewAction(currentActivityView), new ErrorHandler());
    }

    @Override
    public void onStart() {
        super.onStart();
//        locationController.onLocationPermissionGranted();
        onLocationPermissionGranted();
    }

    @Override
    public void onStop() {
        super.onStop();
        unsubscribe(updatableLocationSubscription);
        unsubscribe(addressSubscription);
        unsubscribe(lastKnownLocationSubscription);
        unsubscribe(activitySubscription);
    }

//    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add("Geofencing").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                startActivity(new Intent(MainActivity.this, GeofenceActivity.class));
//                return true;
//            }
//        });
//        menu.add("Places").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if (TextUtils.isEmpty(getString(R.string.API_KEY))) {
//                    Toast.makeText(MainActivity.this, "First you need to configure your API Key - see README.md", Toast.LENGTH_SHORT).show();
//                } else {
//                    startActivity(new Intent(MainActivity.this, PlacesActivity.class));
//                }
//                return true;
//            }
//        });
//        menu.add("Mock Locations").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                startActivity(new Intent(MainActivity.this, MockLocationsActivity.class));
//                return true;
//            }
//        });
        return true;
    }

    private class ErrorHandler implements Action1<Throwable> {
        @Override
        public void call(Throwable throwable) {
            Toast.makeText(getActivity(), "Error occurred.", Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", "Error occurred", throwable);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);//intent);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                //Reference: https://developers.google.com/android/reference/com/google/android/gms/location/SettingsApi
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        Log.d(TAG, "User enabled location");
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        Log.d(TAG, "User Cancelled enabling location");
                        break;
                    default:
                        break;
                }
                break;
        }
    }

}
