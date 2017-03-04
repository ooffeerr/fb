package com.poc.fb.fb_poc.ui;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.poc.fb.fb_poc.R;
import com.poc.fb.fb_poc.location.NativeLocationProvider;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

/**
 * Native locations fragment
 */
public class NativeLocationsFragment extends Fragment {

    private static final String TAG = "NativeLocationsFragment";
    @BindView(R.id.lastKnownLocationTextView) TextView lastKnownLocationTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.native_location_fragment, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
        NativeLocationProvider nativeLocationProvider = new NativeLocationProvider();
        Observable<Location> gpsLocationsObservable = nativeLocationProvider.getGpsLocationsObservable(getActivity(), TimeUnit.SECONDS.toMillis(1), 0);
        gpsLocationsObservable.subscribe(new Subscriber<Location>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted() called");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError() called with: e = [" + e + "]");
            }

            @Override
            public void onNext(Location location) {
                Log.d(TAG, "onNext() called with: location = [" + location + "]");
                lastKnownLocationTextView.setText(location.toString());
            }
        });
    }
}
