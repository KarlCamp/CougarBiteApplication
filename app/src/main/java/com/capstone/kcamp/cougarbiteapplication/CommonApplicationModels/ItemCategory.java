package com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels;

/**
 * The ItemCategory class models the food item model detailed in Firebase.
 * In other words, the class is reflective of the JSON object stored in the
 * database and properly parses object values by having identical parameter names.
 * Stores basic information about food items in a category and helps populate
 * recycler view of ItemScreenViewHolder.
 *
 * @author Karl Camp
 * @version 1.0.0
 * @since 2019-05-04
 */
public class ItemCategory {
    private String name; //stores food item name
    private String description; //stores food item description
    private String price; //stores food item price
    private String nutritionalValue; //stores food item caloric content information
    private String foodIdentificationNumber; //stores food item unique identifier

    //generated appropriate setters and getters

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

    public void setNutritionalValue(String nutritionalValue) { this.nutritionalValue = nutritionalValue; }

    public String getMenuId() {
        return foodIdentificationNumber;
    }

    public void setMenuId(String foodIdentificationNumber) { this.foodIdentificationNumber = foodIdentificationNumber; }

    public String getText() {
        return name;
    }

    public void setText(String text) {
        this.name = text;
    }

    //generated appropriate constructor initializing all class variables

    public ItemCategory(String name, String description, String price, String nutritionalValue, String foodIdentificationNumber) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.nutritionalValue = nutritionalValue;
        this.foodIdentificationNumber = foodIdentificationNumber;
    }

    //generated appropriate default constructor

    public ItemCategory() {}
}