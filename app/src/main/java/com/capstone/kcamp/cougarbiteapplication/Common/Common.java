package com.capstone.kcamp.cougarbiteapplication.Common;

import com.capstone.kcamp.cougarbiteapplication.Model.CreditCard;
import com.capstone.kcamp.cougarbiteapplication.Model.Customer;
import com.capstone.kcamp.cougarbiteapplication.Model.Order;
import com.capstone.kcamp.cougarbiteapplication.Model.Request;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Common {
    public static Customer currentCustomer;
    public static CreditCard creditCard=new CreditCard();
    public static DecimalFormat df = new DecimalFormat("0.00");
    public static Request request;
    public static final String HNUMBER_KEY="HNumber";
    public static final String PHONE_KEY="Phone";
    public static final String PWD_KEY="Password";
    public static final String CREDIT_CARD_NUMBER="";
    public static final String CREDIT_CARD_NAME="";
    public static final String CREDIT_CARD_CVV="";
    public static List<Order> cart = new ArrayList<>();
    public static List<Double> prices = new ArrayList<>();
    public static double total=0;
    public static String convertCodeToStatus(String status) {
        if (status.equals("0"))
            return "Placed";
        else if(status.equals("1"))
            return "Cooking";
        else if(status.equals("2"))
            return "Prepared";
        else return "Picked Up";
    }
}
