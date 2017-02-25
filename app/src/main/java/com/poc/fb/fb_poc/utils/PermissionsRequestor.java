package com.poc.fb.fb_poc.utils;

import android.Manifest;
import android.content.Context;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

/**
 * Created by oferschonberger on 25/02/17.
 */

public class PermissionsRequestor {

    public void requestPermissions(final Context context, final IPermissionsListener listener) {
        RxPermissions
                .getInstance(context)
                .request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            listener.onPermissionsGranted();
                        } else {
                            listener.onPermissionsRevoked();
                        }
                    }
                });
    }
}
