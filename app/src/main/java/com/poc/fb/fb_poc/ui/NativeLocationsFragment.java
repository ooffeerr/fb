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

import com.poc.fb.fb_poc.FbApplication;
import com.poc.fb.fb_poc.R;
import com.poc.fb.fb_poc.location.ILocationUpdateUIListener;
import com.poc.fb.fb_poc.logic.ILocationController;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Native locations fragment
 */
public class NativeLocationsFragment extends Fragment implements ILocationUpdateUIListener {

    private static final String TAG = "NativeLocationsFragment";
    @BindView(R.id.lastKnownLocationTextView) TextView lastKnownLocationTextView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((FbApplication) getActivity().getApplication()).component().inject(this);
        controller.setListener(this);
    }

    @Inject ILocationController controller;

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
        controller.startTrackingLocations();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (controller != null) {
            controller.stopTrackingLocations();
        }
    }

    @Override
    public void updateDisplayWithLocation(Location location) {
        lastKnownLocationTextView.setText(location.toString());
    }
}
