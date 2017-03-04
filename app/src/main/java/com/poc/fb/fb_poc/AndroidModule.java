package com.poc.fb.fb_poc;

import android.content.Context;
import android.location.LocationManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import android.content.Context;
import android.location.LocationManager;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

import static android.content.Context.LOCATION_SERVICE;

/**
 * A module for Android-specific dependencies which require a {@link Context} or
 * {@link android.app.Application} to create.
 */

@Module
public class AndroidModule {

        private final FbApplication application;

        public AndroidModule(FbApplication application) {
            this.application = application;
        }

        /**
         * Allow the application context to be injected but require that it be annotated with
         * {@link ForApplication @Annotation} to explicitly differentiate it from an activity context.
         */
        @Provides
        @Singleton
//        @ForApplication
        Context provideApplicationContext() {
            return application;
        }

        @Provides @Singleton
        LocationManager provideLocationManager() {
            return (LocationManager) application.getSystemService(LOCATION_SERVICE);
        }

}
