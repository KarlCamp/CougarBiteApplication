package com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels;

import java.util.List;

/**
 * The Request class models the order request stored in Firebase. In other words,
 * the request class creates a particular order request and stores basic information
 * regarding an order onto Firebase. It is means by which the customerrequests and
 * employeerequests databases communicate effectively.
 *
 * @author Karl Camp
 * @version 1.0.0
 * @since 2019-05-04
 */
public class Request {
    private String phone; //stores customer phone number
    private String hNumber; //stores customer hNumber
    private String pickUpTime; //stores pick up time detailed by the customer
    private String orderDetails; //stores string generated by parsing through order
    private String total; //stores total amount spent on order
    private String status; //stores current status of order

    //generated appropriate setters and getters

    public String gethNumber() { return hNumber; }

    public void sethNumber(String hNumber) { this.hNumber = hNumber; }

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

    //generated appropriate constructor initializing all class variables

    public Request(String phone, String hNumber, String pickUpTime, String orderDetails, String total) {
        this.phone = phone;
        this.hNumber = hNumber;
        this.pickUpTime = pickUpTime;
        this.orderDetails = orderDetails;
        this.total = total;
        this.status = "0";
    }

    //generated appropriate default constructor

    public Request() {}
}