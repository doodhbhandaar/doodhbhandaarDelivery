package com.doodhbhandaar.deliverydoodhbhandaar;

import android.app.ProgressDialog;
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

import org.apache.http.conn.ManagedHttpClientConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;

public class TodayDeliveryList extends AppCompatActivity {

    ArrayList<CustomerData> customerDataArrayList;
    RecyclerView recyclerView;
    DatabaseReference customersReference;
    String deliveryBoyPhoneNumber;
    CustomerAdapter customerAdapter;
    ClickInterface clickInterface;
    Button startButton;
    double initialLatitude=25.4591136;
    double initialLongitude=78.6404257;
    boolean isMorning;
    ArrayList<CustomerData> unsortedCustomerData=new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_delivery_list);

        recyclerView=findViewById(R.id.recyclerView);
        startButton=findViewById(R.id.start_button);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        deliveryBoyPhoneNumber="10";
        customersReference = FirebaseInstanse.getDatabaseInstance().getReference("CUSTOMERS");

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if(hour<=13)
            isMorning=true;
        else
            isMorning=false;

        Query customerQuery = customersReference.orderByChild("deliveryBoyContactNumber").equalTo(deliveryBoyPhoneNumber);

        customerQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren()){
//                    Log.i("c00","enter");
                    CustomerData customerData = d.getValue(CustomerData.class);
                   // if(customerData.isMorning && isMorning)
                     //   unsortedCustomerData.add(customerData);
                    //else if(customerData.isEvening && !isMorning)
                        unsortedCustomerData.add(customerData);
                }
                progressDialog.dismiss();
                sortDeliveries();
                Log.i("c00",customerDataArrayList.size()+" "+unsortedCustomerData.size());
                customerAdapter.notifyDataSetChanged();
                DataTransfer.customerDataTransfer.addAll(customerDataArrayList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //DataTransfer.customerDataTransfer=new ArrayList<>();
        customerDataArrayList=new ArrayList<>();
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

    private void sortDeliveries() {
        double[][] sortArray = new double[unsortedCustomerData.size()][2];
        for (int i=0;i<sortArray.length;i++){
            double distance = Math.abs(initialLatitude-Double.parseDouble(unsortedCustomerData.get(i).latitude));
            distance+= Math.abs(initialLongitude-Double.parseDouble(unsortedCustomerData.get(i).longitude));
            sortArray[i][0]=distance;
            sortArray[i][1]=i;
        }
        Arrays.sort(sortArray, new Comparator<double[]>() {
            @Override
            // Compare values according to columns
            public int compare(final double[] entry1,
                               final double[] entry2) {

                if (entry1[0] > entry2[0])
                    return 1;
                else
                    return -1;
            }
        });


        for(int i=0;i<unsortedCustomerData.size();i++){
            customerDataArrayList.add(unsortedCustomerData.get((int)(sortArray[i][1])));
        }


    }




}