package com.poc.fb.fb_poc.utils;

import android.Manifest;
import android.content.Context;
import android.database.Observable;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

/**
 * Created by oferschonberger on 25/02/17.
 */

public class PermissionsRequester {

    public Context context;

    public PermissionsRequester(Context context) {
        this.context = context;
    }

    public void requestPermissions(final IPermissionsListener listener) {
                getPermissionsObservable().subscribe(new Action1<Boolean>() {
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

    public rx.Observable<Boolean> getPermissionsObservable() {
        return  RxPermissions
                .getInstance(context)
                .request(Manifest.permission.ACCESS_FINE_LOCATION);
    }
}
