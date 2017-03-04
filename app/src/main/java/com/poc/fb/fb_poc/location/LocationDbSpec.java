package com.poc.fb.fb_poc.location;

import android.os.Bundle;

import com.yahoo.squidb.annotations.ColumnSpec;
import com.yahoo.squidb.annotations.PrimaryKey;
import com.yahoo.squidb.annotations.TableModelSpec;

/**
 * Created by oferschonberger on 27/02/17.
 */
@TableModelSpec(className = "Location", tableName = "locations")
public class LocationDbSpec {

    @PrimaryKey
    long _id;

    @ColumnSpec(defaultValue = "Gps", constraints = "NOT NULL")
    String provider;

    @ColumnSpec(defaultValue = "0", constraints = "NOT NULL")
    long time;

    @ColumnSpec(defaultValue = "0", constraints = "NOT NULL")
    long elapsedRealtimeNanos;

    @ColumnSpec(defaultValue = "0", constraints = "NOT NULL")
    byte fieldsMask;

    @ColumnSpec(defaultValue = "0", constraints = "NOT NULL")
    private double latitude = 0.0;

    @ColumnSpec(defaultValue = "0", constraints = "NOT NULL")
    private double longitude = 0.0;

    @ColumnSpec(defaultValue = "0", constraints = "NOT NULL")
    private double altitude = 0.0f;

    @ColumnSpec(defaultValue = "0", constraints = "NOT NULL")
    private float speed = 0.0f;

    @ColumnSpec(defaultValue = "0", constraints = "NOT NULL")
    private float bearing = 0.0f;

    @ColumnSpec(defaultValue = "0", constraints = "NOT NULL")
    private float accuracy = 0.0f;

}