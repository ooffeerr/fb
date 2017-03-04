package com.poc.fb.fb_poc;

import android.app.Application;
import android.location.LocationManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by oferschonberger on 03/03/17.
 */

public class FbApplication extends Application {

    @Singleton
    @Component(modules = AndroidModule.class)

    public interface ApplicationComponent {
        void inject(FbApplication application);
    }

    @Inject  LocationManager locationManager; // for some reason.

    private ApplicationComponent component;

    @Override public void onCreate() {
        super.onCreate();
        component = DaggerFbApplication_ApplicationComponent.builder()
                .androidModule(new AndroidModule(this))
                .build();
        component().inject(this); // As of now, LocationManager should be injected into this.
    }

    public ApplicationComponent component() {
        return component;
    }
}


