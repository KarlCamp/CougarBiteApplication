package com.capstone.kcamp.cougarbiteapplication.Model;
public class Customer {
    private String Password;
    private String Phone;
    private String HNumber;
    private String Meals;
    private String Cash;
    public Customer() { }
    public Customer(String hNumber, String password, String phone, String meals, String cougarCash) {
        HNumber = hNumber;
        Password = password;
        Phone = phone;
        Meals = meals;
        Cash = cougarCash;
    }
    public String getHNumber() {
        return HNumber;
    }
    public void setHNumber(String name) {
        HNumber = name;
    }
    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        Password = password;
    }
    public String getPhone() {
        return Phone;
    }
    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getMeals() {
        return Meals;
    }

    public void setMeals(String meals) {
        Meals = meals;
    }

    public String getCash() {
        return Cash;
    }

    public void setCash(String cash) {
        Cash = cash;
    }
}