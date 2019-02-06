package com.doodhbhandaar.deliverydoodhbhandaar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class ToadayDelivery extends  FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<CustomerData> customerDataArrayList;
    DatabaseReference customersReference;
    DatabaseReference deleviryBoyReference;
    ArrayList<LatLng> coordList;
    String deliveryBoyPhoneNumber;
    DeliveryBoyReference currentDeliveryBoy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toaday_delivery);

        Intent I=getIntent();
       // deliveryBoyPhoneNumber=i.getStringExtra("number");
        deliveryBoyPhoneNumber="10";


        customerDataArrayList=new ArrayList<>();
        customerDataArrayList.addAll(DataTransfer.getData());
        coordList = new ArrayList<>();

          for(int i=0;i<customerDataArrayList.size();i++){
              coordList.add(new LatLng(Double.parseDouble(customerDataArrayList.get(i).latitude),Double.parseDouble(customerDataArrayList.get(i).longitude)));

          }

        //deleviryBoyReference=FirebaseInstanse.getDatabaseInstance().getReference("DELIVERYBOY");


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


//    private void addCustomer() {
//
//        for(int i=0;i<CustomersId.size();i++){
//            Log.i("c00",CustomersId.get(i)+"");
//            String s=""+CustomersId.get(i);
//            Query CustomerQuery=customersReference.orderByChild("customerPhonenumber").equalTo(s);
//            CustomerQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for (DataSnapshot d:dataSnapshot.getChildren()){
//
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    Log.i("c00","cancel");
//                }
//            });
//
//        }
//        addValueOnMap();
//
//    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //PolylineOptions polylineOptions = new PolylineOptions();

}

    private void addValueOnMap() {

        LatLng biet = new LatLng(25.4591136, 78.6404257);
        mMap.addMarker(new MarkerOptions().position(biet).title("BIET Jhansi"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(biet,14f));

        LatLng c=new LatLng(24.4654645,79.645533);
        Log.i("c00",customerDataArrayList.size()+"dd");

        mMap.addPolyline(
                new PolylineOptions()
                        .add(biet)
                        .addAll(coordList)
                        .width(9f).color(Color.RED)
        );
       // drawpath(biet,c);

    }

    private void drawpath(LatLng sourcePosition, LatLng destPosition) {
        GMapV2Direction md = new GMapV2Direction();

        Document doc = md.getDocument(sourcePosition, destPosition,
                GMapV2Direction.MODE_DRIVING);

        ArrayList<LatLng> directionPoint = md.getDirection(doc);
        PolylineOptions rectLine = new PolylineOptions().width(3).color(
                Color.RED);

        for (int i = 0; i < directionPoint.size(); i++) {
            rectLine.add(directionPoint.get(i));
        }
        Polyline polylin = mMap.addPolyline(rectLine);

    }


}