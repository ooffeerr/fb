package com.poc.fb.fb_poc.location;

import android.location.Location;

/**
 * Contract for location change listeners
 */
public interface ILocationUpdateUIListener {
    void updateDisplayWithLocation(Location location);
}
