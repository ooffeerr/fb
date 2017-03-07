package com.poc.fb.fb_poc;

import android.app.Application;
import android.location.LocationManager;

import com.poc.fb.fb_poc.db.LocationDatabase;
import com.poc.fb.fb_poc.location.LocationModule;
import com.poc.fb.fb_poc.logic.ILocationController;

import javax.inject.Inject;

/**
 * Created by oferschonberger on 03/03/17.
 */

public class FbApplication extends Application {


    @Inject
    LocationManager locationManager;

    @Inject
    ILocationController locationController;

    @Inject
    LocationDatabase database;


    private ApplicationComponent component;

    @Override public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder().
                locationModule(new LocationModule(this))
                .build();
        component().inject(this);
    }

    public ApplicationComponent component() {
        return component;
    }
}
