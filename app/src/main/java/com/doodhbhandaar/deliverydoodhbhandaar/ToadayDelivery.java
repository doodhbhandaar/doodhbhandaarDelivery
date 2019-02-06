package com.doodhbhandaar.deliverydoodhbhandaar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
    ArrayList<LatLng> coordList;
    RecyclerView recyclerView;
    Button previousButton;
    Button nextButton;
    ArrayList<CustomerData> recyclerList;
    CustomerAdapter customerAdapter;
    ClickInterface clickInterface;
    Marker prevMarker;
    int Index=0;
    int MaxSize=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toaday_delivery);
         recyclerView=findViewById(R.id.recyclerView_present);
         previousButton=findViewById(R.id.previous_button);
         nextButton=findViewById(R.id.next_button);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

       // Intent I=getIntent();
       // deliveryBoyPhoneNumber=i.getStringExtra("number");




    }

    private void updateView() {

        recyclerList.clear();
        recyclerList.add(customerDataArrayList.get(Index));
        customerAdapter.notifyDataSetChanged();
        addValueOnMap(coordList.get(Index),customerDataArrayList.get(Index).customerName);

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
        mapIsready();
        //PolylineOptions polylineOptions = new PolylineOptions();

   }

    private void mapIsready() {

        clickInterface=new ClickInterface() {
            @Override
            public void itemClick(View item, int position) {
                return;
            }
        };
        recyclerList=new ArrayList<>();
        customerAdapter=new CustomerAdapter(this,recyclerList,clickInterface);
        recyclerView.setAdapter(customerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));



        customerDataArrayList=new ArrayList<>();
        customerDataArrayList.addAll(DataTransfer.getData());
        coordList = new ArrayList<>();
        Log.i("dTaa",customerDataArrayList.size()+"");
        MaxSize=customerDataArrayList.size();
        for(int i=0;i<customerDataArrayList.size();i++){
            coordList.add(new LatLng(Double.parseDouble(customerDataArrayList.get(i).latitude),Double.parseDouble(customerDataArrayList.get(i).longitude)));

        }
        recyclerList.add(customerDataArrayList.get(Index));
        customerAdapter.notifyDataSetChanged();
        addValueOnMap(coordList.get(Index),customerDataArrayList.get(Index).customerName);


        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Index<=0){
                    Toast.makeText(ToadayDelivery.this,"This is First Delivery",Toast.LENGTH_LONG).show();
                    return;
                }
                Index--;
                updateView();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Index>=MaxSize-1){
                    Toast.makeText(ToadayDelivery.this,"This is Last Delivery",Toast.LENGTH_LONG).show();
                    return;
                }
                Index++;
                    updateView();


            }
        });


    }

    private void addValueOnMap(LatLng loc,String name) {

        //LatLng biet = new LatLng(25.4591136, 78.6404257);
        if(Index!=0&&prevMarker!=null){
            prevMarker.remove();
        }
        prevMarker = mMap.addMarker(new MarkerOptions().position(loc).title(name));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc,14f));

        LatLng c=new LatLng(24.4654645,79.645533);
        Log.i("c00",customerDataArrayList.size()+"dd");

//        mMap.addPolyline(
//                new PolylineOptions()
//                        .add(biet)
//                        .addAll(coordList)
//                        .width(9f).color(Color.RED)
//        );
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