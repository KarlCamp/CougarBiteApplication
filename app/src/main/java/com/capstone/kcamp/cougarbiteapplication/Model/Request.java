package com.capstone.kcamp.cougarbiteapplication.Model;

import java.util.ArrayList;
import java.util.List;

public class Request {
    private String phone;
    private String name;
    private String pickUpTime;
    private String orderDetails;
    private String total;
    private List<Order> ls;
    private String status;

    public Request() {

    }

    public Request(String phone, String name, String pickUpTime, String orderDetails, String total, List<Order> ls) {
        this.phone = phone;
        this.name = name;
        this.pickUpTime = pickUpTime;
        this.orderDetails = orderDetails;
        this.total = total;
        this.ls = ls;
        this.status = "0";
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getLs() {
        return ls;
    }

    public void setLs(List<Order> ls) {
        this.ls = ls;
    }
}
