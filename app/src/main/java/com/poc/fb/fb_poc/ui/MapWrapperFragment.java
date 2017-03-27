package com.poc.fb.fb_poc.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.poc.fb.fb_poc.R;

import butterknife.ButterKnife;

/**
 * A wrapper fragment to the maps fragment - adding all sorts of goodies (current location and locations path)
 */
public class MapWrapperFragment extends Fragment implements
        GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback {
    private static final String TAG = "MapWrapperFragment";
    private GoogleMap mMap;
//    SupportMapFragment

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        ((FbApplication) getActivity().getApplication()).component().inject(this);
//        controller.setListener(this);
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_wrapper_fragment, null);
        ButterKnife.bind(this, view);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }


    @Override
    public boolean onMyLocationButtonClick() {
        Log.d(TAG, "onMyLocationButtonClick() called");
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady() called with: googleMap = [" + googleMap + "]");
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        //enableMyLocation();
        mMap.setMyLocationEnabled(true);
    }
}
