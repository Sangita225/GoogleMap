package com.example.googlemap;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Camera;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.googlemap.model.LatitudeLongitudeClass;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private AutoCompleteTextView etCity;
    private Button btnsearch;
    private List<LatitudeLongitudeClass>latitudeLongitudelist;
    Marker markerName;
    CameraUpdate center;
    CameraUpdate zoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);
        etCity=findViewById(R.id.etCity);
        btnsearch=findViewById(R.id.btnseaarch);
        fillArrayListAndAdapter();

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etCity.getText().toString())){
                    etCity.setError("please enter a place name");
                    return;
                }
                int position=SearchArrayList(etCity.getText().toString());
                if(position>-1) {
                    loadMap(position);
                }
                    else{
                    Toast.makeText(SearchActivity.this,"Location not found:"+ etCity.getText().toString(),Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    private void fillArrayListAndAdapter(){
    latitudeLongitudelist=new ArrayList<>();
        latitudeLongitudelist.add(new LatitudeLongitudeClass(27.706195,85.3300396,"Mero college"));
        latitudeLongitudelist.add(new LatitudeLongitudeClass(27.7051477,85.3287151,"Burger House"));
        latitudeLongitudelist.add(new LatitudeLongitudeClass(27.7049619,85.3291342,"Pipalbot Bus park"));

        String[]data=new String[latitudeLongitudelist.size()];
        for(int i=0; i<data.length;i++){
            data[i]=latitudeLongitudelist.get(i).getMarker();
        }

        ArrayAdapter<String>adapter=new ArrayAdapter<>(
                SearchActivity.this,android.R.layout.simple_list_item_1,data
        );
        etCity.setAdapter(adapter);
        etCity.setThreshold(1);

    }

    public  int SearchArrayList(String name){
        for(int i=0; i<latitudeLongitudelist.size();i++){
            if(latitudeLongitudelist.get(i).getMarker().contains(name)){
                return 1;
            }
        }
        return -1;
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap=googleMap;
        center= CameraUpdateFactory.newLatLng(new LatLng(27.706195, 85.3300396));
        zoom= CameraUpdateFactory.zoomTo(15);
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }


    public void loadMap(int position){
        if(markerName!=null){
            markerName.remove();
        }
        double latitude=latitudeLongitudelist.get(position).getLat();
        double longitude=latitudeLongitudelist.get(position).getLon();
        String marker=latitudeLongitudelist.get(position).getMarker();
        center=CameraUpdateFactory.newLatLng(new LatLng(latitude,longitude));
        markerName= mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(marker));
        zoom=CameraUpdateFactory.zoomTo( 17 );
        mMap.moveCamera(center);
        mMap.animateCamera( zoom );
        mMap.getUiSettings().setZoomControlsEnabled(true);



    }
}
