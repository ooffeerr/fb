package com.poc.fb.fb_poc.utils;

/**
 * Permissions queries listener.
 */
public interface IPermissionsListener {

    void onPermissionsGranted();

    void onPermissionsRevoked();
}
