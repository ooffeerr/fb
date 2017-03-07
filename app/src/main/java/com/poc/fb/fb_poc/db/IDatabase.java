package com.poc.fb.fb_poc.db;

/**
 * Locations storage contact.
 */
public interface IDatabase {

    /**
     * Persists a location
     * @param location location for persistence
     */
    void addLocation(android.location.Location location);
}
