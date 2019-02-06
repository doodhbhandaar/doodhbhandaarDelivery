package com.doodhbhandaar.deliverydoodhbhandaar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TodayDeliveryList extends AppCompatActivity {

    ArrayList<CustomerData> customerDataArrayList;

    DatabaseReference customersReference;
    String deliveryBoyPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_delivery_list);


        customersReference = FirebaseInstanse.getDatabaseInstance().getReference("CUSTOMERS");


        Query customerQuery = customersReference.orderByChild("deliveryBoyContactNumber").equalTo(deliveryBoyPhoneNumber);

        customerQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    Log.i("c00","enter");
                    CustomerData customerData = d.getValue(CustomerData.class);
                    customerDataArrayList.add(customerData);
                  }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


         DataTransfer.customerDataTransfer.addAll(customerDataArrayList);

    }
}
