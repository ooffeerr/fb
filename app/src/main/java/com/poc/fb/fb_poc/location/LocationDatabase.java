package com.poc.fb.fb_poc.location;


import com.yahoo.squidb.annotations.ColumnSpec;
import com.yahoo.squidb.annotations.TableModelSpec;
import com.yahoo.squidb.data.ISQLiteDatabase;
import com.yahoo.squidb.data.ISQLiteOpenHelper;
import com.yahoo.squidb.data.SquidDatabase;
import com.yahoo.squidb.sql.Query;
import com.yahoo.squidb.sql.Table;


/**
 * Stores all detected locations forever.
 */
public class LocationDatabase extends SquidDatabase {

    private static final int VERSION = 1;

    public LocationDatabase() {
        super();
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
        return null;
    }

    @Override
    protected int getVersion() {
        return VERSION;
    }

    public void addLocation(android.location.Location location) {

        LocationDatabase db = new LocationDatabase();
        Location savedLocation = new Location();
        savedLocation.setAccuracy((double) location.getAccuracy())
                .setAltitude(location.getAltitude())
                .setBearing((double) location.getBearing())
//                .setElapsedRealtimeNanos(location.getElapsedRealtimeNanos())
                .setLatitude(location.getLatitude())
                .setLongitude(location.getLongitude())
//                .setFieldsMask(location.has)
                .setProvider(location.getProvider());


        db.persist(savedLocation);
    }

    // Other overridable methods exist for migrations and initialization;
    // omitted for brevity
}
