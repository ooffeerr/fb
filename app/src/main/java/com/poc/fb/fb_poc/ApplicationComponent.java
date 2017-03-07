package com.poc.fb.fb_poc;

import com.poc.fb.fb_poc.location.LocationModule;
import com.poc.fb.fb_poc.location.NativeLocationProvider;
import com.poc.fb.fb_poc.ui.NativeLocationsFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by oferschonberger on 06/03/17.
 */

@Singleton
@Component(modules = {LocationModule.class})
public interface ApplicationComponent {
    void inject(FbApplication application);
    void inject(NativeLocationsFragment fragment);
    void inject(NativeLocationProvider provider);
}
