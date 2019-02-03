package com.doodhbhandaar.deliverydoodhbhandaar;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.media.CamcorderProfile.get;

public class ToadayDelivery extends  FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<CustomerData> customerDataArrayList;
    DatabaseReference customersReference;
    ArrayList<LatLng> coordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toaday_delivery);

        customerDataArrayList=new ArrayList<>();
        coordList = new ArrayList<>();


        customersReference = FirebaseInstanse.getDatabaseInstance().getReference("CUSTOMERS");
        customersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d:dataSnapshot.getChildren()){
                    CustomerData customerData = d.getValue(CustomerData.class);
                    customerDataArrayList.add(customerData);
                    coordList.add(new LatLng(Double.parseDouble(customerData.latitude),Double.parseDouble(customerData.longitude)));

                }

                addValueOnMap();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //PolylineOptions polylineOptions = new PolylineOptions();

}

    private void addValueOnMap() {

        LatLng biet = new LatLng(25.4591136, 78.6404257);
        mMap.addMarker(new MarkerOptions().position(biet).title("BIET Jhansi"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(biet,14f));


        Log.i("c00",customerDataArrayList.size()+"dd");

        mMap.addPolyline(
                new PolylineOptions()
                        .add(biet)
                        .addAll(coordList)
                        .width(9f).color(Color.RED)
        );

    }


}