package com.doodhbhandaar.deliverydoodhbhandaar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerViewholder> {

    ArrayList<CustomerData> customerDataArrayList;
    LayoutInflater inflater;
    ClickInterface clickInterface;

    public CustomerAdapter(Context context, ArrayList<CustomerData> customerDataArrayList, ClickInterface clickInterface){
        inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.customerDataArrayList=customerDataArrayList;
        this.clickInterface=clickInterface;
    }

    @NonNull
    @Override
    public CustomerViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View output=inflater.inflate(R.layout.customer_view,parent,false);
        return new CustomerViewholder(output);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewholder holder, final int position) {
        CustomerData customerData=customerDataArrayList.get(position);
        holder.customerName.setText(customerData.customerName);
        holder.customerPhonenumber.setText(customerData.customerPhonenumber);
        holder.customerAddress.setText(customerData.customerAddress);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickInterface.itemClick(v,position);
            }
        });
//        Picasso.get().load(R.drawable.customer_pic)
//                .fit().placeholder(R.color.colorPrimaryDark)
//                .into(holder.customerImage);
    }

    @Override
    public int getItemCount() {
        return customerDataArrayList.size();
    }
}