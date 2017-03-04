package com.poc.fb.fb_poc;

import android.app.Application;

/**
 * Created by oferschonberger on 03/03/17.
 */

public class FbApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = Dagger.builder().applicationModule(this).build();
    }
}
