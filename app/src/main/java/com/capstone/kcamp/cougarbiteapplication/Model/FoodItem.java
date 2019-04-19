package com.capstone.kcamp.cougarbiteapplication.Model;

public class FoodItem {
    String text;
    String description;
    String price;
    String nutritionalValue;
    String menuId;
    public FoodItem() {

    }

    public FoodItem(String text, String description, String price, String nutritionalValue, String menuId) {
        this.text = text;
        this.description = description;
        this.price = price;
        this.nutritionalValue = nutritionalValue;
        this.menuId = menuId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNutritionalValue() {
        return nutritionalValue;
    }

    public void setNutritionalValue(String nutritionalValue) {
        this.nutritionalValue = nutritionalValue;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

