package com.doodhbhandaar.deliverydoodhbhandaar;

import java.util.ArrayList;

public class DataTransfer {

  static  ArrayList<CustomerData> customerDataTransfer=new ArrayList<>();

    public static ArrayList<CustomerData> getData(){
        return customerDataTransfer;
    }
    public static ArrayList<CustomerData> putData(){
        if(customerDataTransfer==null)
            customerDataTransfer=new ArrayList<>();
        return  customerDataTransfer;
    }

}
