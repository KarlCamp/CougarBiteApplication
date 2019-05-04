package com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels;

/**
 * The Customer class models the customer model detailed in Firebase.
 * In other words, the class is reflective of the JSON object stored in the
 * database and properly parses object values by having identical parameter names.
 * Stores basic information regarding a customer.
 *
 * @author Karl Camp
 * @version 1.0.0
 * @since 2019-05-04
 */
public class Customer {
    private String password; //stores customer password (based on crf_email password)
    private String phone; //stores customer phone (contact information regarding order)
    private String hNumber; //stores customer hNumber (not part of JSON object, gets set later)
    private String meals; //stores customer meal balance
    private String cash; //stores customer cash balance

    //generated appropriate setters and getters

    public String getHNumber() {
        return hNumber;
    }

    public void setHNumber(String hNumber) {
        this.hNumber = hNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMeals() { return meals; }

    public void setMeals(String meals) {
        this.meals = meals;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    //generated appropriate constructor initializing all class variables

    public Customer(String hNumber, String password, String phone, String meals, String cougarCash) {
        this.hNumber = hNumber;
        this.password = password;
        this.phone = phone;
        this.meals = meals;
        this.cash = cougarCash;
    }

    //generated appropriate default constructor

    public Customer() {}
}