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
    public static List<Order> cart = new ArrayList<>();
    public static List<Double> prices = new ArrayList<>();
    public static final String UPDATE = "Update";
    public static final String DELETE = "Update";
    public static double total=0;
    public static String convertCodeToStatus(String status) {
        if (status.equals("0"))
            return "Placed";
        else if(status.equals("1"))
            return "On My Way";
        else return "Shipped";
    }
}
