//package com.optus.optusappjson;
//
//import android.os.Bundle;
//import com.google.android.m4b.maps.CameraUpdateFactory;
//import com.google.android.m4b.maps.GoogleMap;
//import com.google.android.m4b.maps.OnMapReadyCallback;
//import com.google.android.m4b.maps.SupportMapFragment;
//import com.google.android.m4b.maps.model.LatLng;
//import com.google.android.m4b.maps.model.MarkerOptions;
//import android.support.v4.app.FragmentActivity;
//
//
//public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//    }
//
//    @Override
//    public void onMapReady(GoogleMap map) {
//        // Add a marker in Sydney, Australia, and move the camera.
//        LatLng sydney = new LatLng(-34, 151);
//        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }
//}