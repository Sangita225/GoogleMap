package com.example.googlemap;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.googlemap.model.LatitudeLongitudeClass;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<LatitudeLongitudeClass>latlangs=new ArrayList<>();
        latlangs.add(new LatitudeLongitudeClass(27.706195,85.3300396,"Mero college"));
        latlangs.add(new LatitudeLongitudeClass(27.7051477,85.3287151,"Burger House"));
        latlangs.add(new LatitudeLongitudeClass(27.7049619,85.3291342,"Pipalbot Bus park"));
        CameraUpdate center,zoom;
        for(int i=0; i<latlangs.size();i++){
            center=CameraUpdateFactory.newLatLng(new LatLng(latlangs.get(i).getLat(),latlangs.get(i).getLon()));
            zoom=CameraUpdateFactory.zoomTo(16);
            mMap.addMarker(new MarkerOptions().position(new LatLng(latlangs.get(i).getLat(),latlangs.get(i).getLon())).title(latlangs.get(i).getMarker()));
            mMap.moveCamera(center);
            mMap.animateCamera(zoom);
            mMap.getUiSettings().setZoomControlsEnabled(true);
        }

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(27.706195, 85.3300396);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Mero College"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
