package com.poc.fb.fb_poc.location;

import android.location.Location;

import rx.Observable;

/**
 * Created by oferschonberger on 13/03/17.
 */
public interface ILocationProvider {

    Observable<Location> getGpsLocationsObservable(long minTime, float minDistance);
}
