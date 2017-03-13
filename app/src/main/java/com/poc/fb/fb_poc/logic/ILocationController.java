package com.poc.fb.fb_poc.logic;

import com.poc.fb.fb_poc.location.ILocationUpdateUIListener;

/**
 * Location related logic contract
 */
public interface ILocationController {

    void setListener(ILocationUpdateUIListener listener);

    void startTrackingLocations();

    void stopTrackingLocations();
}
