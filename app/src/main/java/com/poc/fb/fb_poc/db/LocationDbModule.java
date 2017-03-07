package com.poc.fb.fb_poc.db;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by oferschonberger on 06/03/17.
 */
@Module
public class LocationDbModule {

    @Provides
    public LocationDatabase provideLocationsDb(Context context) {
        return new LocationDatabase(context);
    }
}
