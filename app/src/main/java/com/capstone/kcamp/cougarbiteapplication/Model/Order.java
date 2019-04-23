package com.capstone.kcamp.cougarbiteapplication.Model;

public class Order {
    private String productid;
    private String productname;
    private String quantity;
    private String price;

    public Order() {

    }

    public Order(String productid, String productname, String quantity, String price) {
        this.productid = productid;
        this.productname = productname;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
