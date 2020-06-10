package com.example.lordseye;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsEvent extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_event);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(19.211970, 72.872995),
                        new LatLng(19.211342, 72.872952),
                        new LatLng(19.211354, 72.872702),
                        new LatLng(19.210977, 72.872028),
                        new LatLng(19.210519, 72.871749),
                        new LatLng(19.210327, 72.871848),
                        new LatLng(19.210314, 72.872102),
                        new LatLng(19.210318, 72.872684)
                ));

        polyline1.setTag("A");


        LatLng sydney = new LatLng(19.210623, 72.872968);
//        19.210326, 72.873075
        mMap.addMarker(new MarkerOptions().position(sydney).title("Parking for Ganpati Festival"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(19.210623, 72.872968), 17));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}
