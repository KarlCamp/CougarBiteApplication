package com.capstone.kcamp.cougarbiteapplication.Model;
public class FoodItem {
    private String text;
    private String description;
    private String price;
    private String nutritionalValue;
    private String foodIdentificationNumber;
    public FoodItem() { }
    public FoodItem(String text, String description, String price, String nutritionalValue, String foodIdentificationNumber) {
        this.text = text;
        this.description = description;
        this.price = price;
        this.nutritionalValue = nutritionalValue;
        this.foodIdentificationNumber = foodIdentificationNumber;
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
        return foodIdentificationNumber;
    }
    public void setMenuId(String foodIdentificationNumber) {
        this.foodIdentificationNumber = foodIdentificationNumber;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}