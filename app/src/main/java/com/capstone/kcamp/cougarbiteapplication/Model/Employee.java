package com.capstone.kcamp.cougarbiteapplication.Model;

public class Employee {
    private String name;
    private String password;

    public Employee() {
    }

    public Employee(String name, String password, String phone) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
