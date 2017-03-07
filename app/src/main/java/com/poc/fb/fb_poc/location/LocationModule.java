package com.poc.fb.fb_poc.location;

import android.content.Context;
import android.location.LocationManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import com.poc.fb.fb_poc.FbApplication;
import com.poc.fb.fb_poc.db.IDatabase;
import com.poc.fb.fb_poc.db.LocationDatabase;
import com.poc.fb.fb_poc.logic.ILocationController;
import com.poc.fb.fb_poc.logic.LocationController;

import static android.content.Context.LOCATION_SERVICE;

/**
 * A module for Android-specific dependencies which require a {@link Context} or
 * {@link android.app.Application} to create.
 */

@Module
public class LocationModule {

        private final FbApplication application;

        public LocationModule(FbApplication application) {
            this.application = application;
        }

        @Provides
        Context provideContext() {return application; }

        @Provides
        @Singleton
        FbApplication provideApplicationContext() {
            return application;
        }

        @Provides @Singleton
        LocationManager provideLocationManager() {
            return (LocationManager) application.getSystemService(LOCATION_SERVICE);
        }

        @Provides @Singleton
        ILocationController provideLocationController(LocationController locationController) {
            return locationController;
        }

        @Provides
        IDatabase provideLocationsDatabase(LocationDatabase database) {
            return database;
        }

        @Provides @Singleton
        NativeLocationProvider providerLocationProvider() {
            return new NativeLocationProvider(application);
        }
}
