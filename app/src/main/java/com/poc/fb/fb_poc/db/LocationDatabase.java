package com.poc.fb.fb_poc.db;


import android.content.Context;
import android.util.Log;

import com.yahoo.squidb.android.AndroidOpenHelper;
import com.yahoo.squidb.data.ISQLiteDatabase;
import com.yahoo.squidb.data.ISQLiteOpenHelper;
import com.yahoo.squidb.data.SquidDatabase;
import com.yahoo.squidb.sql.Table;

import javax.inject.Inject;


/**
 * Squid-DB implementation for location storage.
 */
public class LocationDatabase extends SquidDatabase implements IDatabase {

    private static final int VERSION = 1;
    private static final String TAG = "LocationDatabase";
    Context context;

    @Inject
    public LocationDatabase(Context context) {
        super();
        this.context = context;
        // Any other initialization of the instance
    }

    @Override
    public String getName() {
        return "locations-database.db";
    }

    @Override
    protected Table[] getTables() {
        return new Table[]{
                // List all tables here
                Location.TABLE,
        };
    }

    @Override
    protected boolean onUpgrade(ISQLiteDatabase db, int oldVersion, int newVersion) {
        return false;
    }

    @Override
    protected ISQLiteOpenHelper createOpenHelper(String databaseName, OpenHelperDelegate delegate, int version) {
        return new AndroidOpenHelper(context, databaseName, delegate, version);
    }

    @Override
    protected int getVersion() {
        return VERSION;
    }

    @Override
    public void addLocation(android.location.Location location) {
        Log.d(TAG, "addLocation() called with: location = [" + location + "]");

        Location savedLocation = new Location();
        savedLocation.setAccuracy((double) location.getAccuracy())
                .setAltitude(location.getAltitude())
                .setBearing((double) location.getBearing())
//                .setElapsedRealtimeNanos(location.getElapsedRealtimeNanos())
                .setLatitude(location.getLatitude())
                .setLongitude(location.getLongitude())
//                .setFieldsMask(location.has)
                .setProvider(location.getProvider())
                .setElapsedRealtimeNanos(location.getElapsedRealtimeNanos())
                .setTime(location.getTime())
                .setAccuracy((double) location.getAccuracy())
                .setProvider(location.getProvider());


        persist(savedLocation);
    }
}
