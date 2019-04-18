package com.capstone.kcamp.cougarbiteapplication.Model;

public class AppUser {
    private String Password;
    private String Phone;
    private String HNumber;

    public AppUser() {

    }

    public AppUser(String hNumber, String password, String phone) {
        HNumber = hNumber;
        Password = password;
        Phone = phone;
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
}