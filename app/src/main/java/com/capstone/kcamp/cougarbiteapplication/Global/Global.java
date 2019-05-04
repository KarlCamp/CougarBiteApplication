package com.capstone.kcamp.cougarbiteapplication.Global;

import com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels.CreditCard;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels.Customer;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels.Order;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels.Request;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * The Global class stores values to be utilized throughout the applications existence.
 * In other words, in order to remain consistent among classes and to avoid redundant
 * variables across the app, commonly used variables are functions are stored here.
 *
 * @author Karl Camp
 * @version 1.0
 * @since 2019-05-04
 */
public class Global {
    public static Customer presentCustomer; //stores information on current customer after login
    public static CreditCard card = new CreditCard(); //stores information on current credit if necessary
    public static DecimalFormat formatter = new DecimalFormat("0.00"); //ensures that only two decimal points are stored in database
    public static Request orderRequest; //stores request to be sent
    public static final String HNUMBER="HNumber"; //stores HNumber for customer login Remember Me functionality
    public static final String PHONE="Phone"; //stores Phone for customer login Remember Me functionality
    public static final String PASSWORD="Password"; //stores Password for customer login Remember Me functionality
    public static final String CARD_NUMBER=""; //stores Card Number for credit card Remember Me functionality
    public static final String CARD_NAME=""; //stores Card Name for credit card Remember Me functionality
    public static final String CARD_CVV=""; //stores CVV for credit card Remember Me functionality
    public static List<Order> currentCart = new ArrayList<>(); //stores list of Orders for during order creation
    public static List<Double> currentCartPrices = new ArrayList<>(); //stores list of prices for easy update of the total
    public static double currentTotal=0; //stores the total amount calculated by summing all the values from prices list

    /**
     * The method statusConversion takes in the numerical status value from customer and employee databases
     * and converts it to a string in OrderStatus and EmployeeOrderStatus respectively.
     *
     * @param numericalStatus is a string value of the numerical representation of current status stored in Firebase.
     * @return the word representation of the numerical value of the string.
     */
    public static String statusConversion(String numericalStatus) {
        if (numericalStatus.equals("0"))
            return "Placed";
        else if(numericalStatus.equals("1"))
            return "Cooking";
        else if(numericalStatus.equals("2"))
            return "Prepared";
        else return "Picked Up";
    }
}