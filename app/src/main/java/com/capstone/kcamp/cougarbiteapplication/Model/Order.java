package com.capstone.kcamp.cougarbiteapplication.Model;

public class Order {
    private String productid;
    private String productname;
    private String quantity;
    private String price;
    private boolean lettuce;
    private boolean tomato;
    private boolean onion;
    private boolean pickle;
    private boolean bacon;
    private boolean cheese;
    private boolean avocado;
    private boolean fried_egg;
    private boolean chicken;
    private boolean patty;

    public Order() {

    }

    public boolean isBacon() {
        return bacon;
    }

    public void setBacon(boolean bacon) {
        this.bacon = bacon;
    }

    public boolean isCheese() {
        return cheese;
    }

    public void setCheese(boolean cheese) {
        this.cheese = cheese;
    }

    public boolean isAvocado() {
        return avocado;
    }

    public void setAvocado(boolean avocado) {
        this.avocado = avocado;
    }

    public boolean isFried_egg() {
        return fried_egg;
    }

    public void setFried_egg(boolean fried_egg) {
        this.fried_egg = fried_egg;
    }

    public boolean isChicken() {
        return chicken;
    }

    public void setChicken(boolean chicken) {
        this.chicken = chicken;
    }

    public boolean isPatty() {
        return patty;
    }

    public void setPatty(boolean patty) {
        this.patty = patty;
    }

    public Order(String productid, String productname, String quantity, String price, boolean lettuce, boolean tomato, boolean onion, boolean pickle,
                 boolean bacon, boolean cheese, boolean avocado, boolean fried_egg, boolean chicken, boolean patty) {
        this.productid = productid;
        this.productname = productname;
        this.quantity = quantity;
        this.price = price;
        this.lettuce = lettuce;
        this.tomato = tomato;
        this.onion = onion;
        this.pickle = pickle;
        this.bacon = bacon;
        this.cheese = cheese;
        this.avocado = avocado;
        this.fried_egg = fried_egg;
        this.chicken = chicken;
        this.patty = patty;
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

    public boolean isLettuce() {
        return lettuce;
    }

    public void setLettuce(boolean lettuce) {
        this.lettuce = lettuce;
    }

    public boolean isTomato() {
        return tomato;
    }

    public void setTomato(boolean tomato) {
        this.tomato = tomato;
    }

    public boolean isOnion() {
        return onion;
    }

    public void setOnion(boolean onion) {
        this.onion = onion;
    }

    public boolean isPickle() {
        return pickle;
    }

    public void setPickle(boolean pickle) {
        this.pickle = pickle;
    }
}
