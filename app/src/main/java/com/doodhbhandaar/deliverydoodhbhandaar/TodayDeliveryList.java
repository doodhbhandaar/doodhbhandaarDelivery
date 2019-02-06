package com.doodhbhandaar.deliverydoodhbhandaar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
    RecyclerView recyclerView;
    DatabaseReference customersReference;
    String deliveryBoyPhoneNumber;
    CustomerAdapter customerAdapter;
    ClickInterface clickInterface;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_delivery_list);

        recyclerView=findViewById(R.id.recyclerView);
        startButton=findViewById(R.id.start_button);
        customerDataArrayList=new ArrayList<>();

        deliveryBoyPhoneNumber="10";
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
                Log.i("c00",customerDataArrayList.size()+" ");
                customerAdapter.notifyDataSetChanged();
                DataTransfer.customerDataTransfer.addAll(customerDataArrayList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

         //DataTransfer.customerDataTransfer=new ArrayList<>();

         clickInterface=new ClickInterface() {
             @Override
             public void itemClick(View item, int position) {
                 return;
             }
         };
         customerAdapter=new CustomerAdapter(this,customerDataArrayList,clickInterface);
         recyclerView.setAdapter(customerAdapter);
         recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

         startButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i=new Intent(TodayDeliveryList.this,ToadayDelivery.class);
                 startActivity(i);
             }
         });

    }



}
