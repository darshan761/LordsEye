package com.example.lordseye;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Map extends FragmentActivity implements OnMapReadyCallback
         {

             FirebaseDatabase database = FirebaseDatabase.getInstance();
             DatabaseReference myRef = database.getReference("Operators");
             Location currentLocation;
             private String TAG = "Darshan";
             FusedLocationProviderClient fusedLocationProviderClient;
             private static final int REQUEST_CODE = 101;
             @Override
             protected void onCreate(Bundle savedInstanceState) {
                 super.onCreate(savedInstanceState);
                 setContentView(R.layout.activity_map);
                 fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
                 fetchLocation();
             }
             private void fetchLocation() {
                 if (ActivityCompat.checkSelfPermission(
                         this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                         this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                     ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
                     return;
                 }
                 Task<Location> task = fusedLocationProviderClient.getLastLocation();
                 task.addOnSuccessListener(new OnSuccessListener<Location>() {
                     @Override
                     public void onSuccess(Location location) {
                         if (location != null) {
                             currentLocation = location;
//                             Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                             SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                             assert supportMapFragment != null;
                             supportMapFragment.getMapAsync(Map.this);
                         }
                     }
                 });
             }
             @Override
             public void onMapReady(final GoogleMap googleMap) {
                 LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                 MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Your Location!");
                 googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                 googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                 googleMap.addMarker(markerOptions);
                 Circle circle = googleMap.addCircle(new CircleOptions()
                         .center(latLng)
                         .radius(500)
                        .fillColor(0x30ff0000));


                 googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                     @Override
                     public boolean onMarkerClick(Marker marker) {
                         Intent i = new Intent(Map.this, Operator.class);
                         i.putExtra("Lat",marker.getPosition().latitude);
                         i.putExtra("Long",marker.getPosition().longitude);
                         startActivity(i);
                         return false;
                     }
                 });

                 myRef.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {

                       for(DataSnapshot i : dataSnapshot.getChildren()){

                           String name =  i.child("Name").getValue().toString();
                           String lat = i.child("Lat").getValue().toString();
                           String longt = i.child("Long").getValue().toString();

                           LatLng park = new LatLng(Double.parseDouble(lat), Double.parseDouble(longt));
                         googleMap.addMarker(new MarkerOptions().position(park)
                                 .title(name));
                           Log.d(TAG, "Value is: " + name);
                           Toast.makeText(getApplicationContext(),"hello"+name,Toast.LENGTH_LONG).show();
                       }

//
                     }

                     @Override
                     public void onCancelled(DatabaseError error) {
                         // Failed to read value
                         Log.w(TAG, "Failed to read value.", error.toException());
                     }
                 });

             }
             @Override
             public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                 switch (requestCode) {
                     case REQUEST_CODE:
                         if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                             fetchLocation();
                         }
                         break;
                 }
             }

}
