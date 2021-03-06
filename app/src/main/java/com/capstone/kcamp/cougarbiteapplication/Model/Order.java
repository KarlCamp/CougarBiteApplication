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
    private boolean beef;
    private boolean breaded_chicken;
    private boolean black_bean;
    private boolean turkey;
    private boolean grilled_chicken;
    private boolean american;
    private boolean bleu;
    private boolean pepper_jack;
    private boolean swiss;
    private boolean provolone;
    private boolean white_kaiser;
    private boolean wheat_kaiser;
    private boolean spinach_wrap;
    private boolean flour_wrap;
    private boolean flat_bread;
    private boolean garlic_wrap;
    private boolean gluten_free;

    public Order() {

    }

    public Order(String productid, String productname, String quantity, String price,
                 boolean lettuce, boolean tomato, boolean onion, boolean pickle,
                 boolean bacon, boolean cheese, boolean avocado, boolean fried_egg, boolean chicken, boolean patty,
                 boolean beef, boolean breaded_chicken, boolean black_bean, boolean turkey, boolean grilled_chicken,
                 boolean american, boolean bleu, boolean pepper_jack, boolean swiss, boolean provolone,
                 boolean white_kaiser, boolean wheat_kaiser, boolean spinach_wrap, boolean flour_wrap, boolean flat_bread, boolean garlic_wrap, boolean gluten_free) {
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
        this.beef = beef;
        this.breaded_chicken = breaded_chicken;
        this.black_bean = black_bean;
        this.turkey = turkey;
        this.grilled_chicken = grilled_chicken;
        this.american = american;
        this.bleu = bleu;
        this.pepper_jack = pepper_jack;
        this.swiss = swiss;
        this.provolone = provolone;
        this.white_kaiser = white_kaiser;
        this.wheat_kaiser = wheat_kaiser;
        this.spinach_wrap = spinach_wrap;
        this.flour_wrap = flour_wrap;
        this.flat_bread = flat_bread;
        this.garlic_wrap = garlic_wrap;
        this.gluten_free = gluten_free;
    }

    public boolean isBeef() {
        return beef;
    }

    public void setBeef(boolean beef) {
        this.beef = beef;
    }

    public boolean isBreaded_chicken() {
        return breaded_chicken;
    }

    public void setBreaded_chicken(boolean breaded_chicken) {
        this.breaded_chicken = breaded_chicken;
    }

    public boolean isBlack_bean() {
        return black_bean;
    }

    public void setBlack_bean(boolean black_bean) {
        this.black_bean = black_bean;
    }

    public boolean isTurkey() {
        return turkey;
    }

    public void setTurkey(boolean turkey) {
        this.turkey = turkey;
    }

    public boolean isGrilled_chicken() {
        return grilled_chicken;
    }

    public void setGrilled_chicken(boolean grilled_chicken) {
        this.grilled_chicken = grilled_chicken;
    }

    public boolean isAmerican() {
        return american;
    }

    public void setAmerican(boolean american) {
        this.american = american;
    }

    public boolean isBleu() {
        return bleu;
    }

    public void setBleu(boolean bleu) {
        this.bleu = bleu;
    }

    public boolean isPepper_jack() {
        return pepper_jack;
    }

    public void setPepper_jack(boolean pepper_jack) {
        this.pepper_jack = pepper_jack;
    }

    public boolean isSwiss() {
        return swiss;
    }

    public void setSwiss(boolean swiss) {
        this.swiss = swiss;
    }

    public boolean isProvolone() {
        return provolone;
    }

    public void setProvolone(boolean provolone) {
        this.provolone = provolone;
    }

    public boolean isWhite_kaiser() {
        return white_kaiser;
    }

    public void setWhite_kaiser(boolean white_kaiser) {
        this.white_kaiser = white_kaiser;
    }

    public boolean isWheat_kaiser() {
        return wheat_kaiser;
    }

    public void setWheat_kaiser(boolean wheat_kaiser) {
        this.wheat_kaiser = wheat_kaiser;
    }

    public boolean isSpinach_wrap() {
        return spinach_wrap;
    }

    public void setSpinach_wrap(boolean spinach_wrap) {
        this.spinach_wrap = spinach_wrap;
    }

    public boolean isFlour_wrap() {
        return flour_wrap;
    }

    public void setFlour_wrap(boolean flour_wrap) {
        this.flour_wrap = flour_wrap;
    }

    public boolean isFlat_bread() {
        return flat_bread;
    }

    public void setFlat_bread(boolean flat_bread) {
        this.flat_bread = flat_bread;
    }

    public boolean isGarlic_wrap() {
        return garlic_wrap;
    }

    public void setGarlic_wrap(boolean garlic_wrap) {
        this.garlic_wrap = garlic_wrap;
    }

    public boolean isGluten_free() {
        return gluten_free;
    }

    public void setGluten_free(boolean gluten_free) {
        this.gluten_free = gluten_free;
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
