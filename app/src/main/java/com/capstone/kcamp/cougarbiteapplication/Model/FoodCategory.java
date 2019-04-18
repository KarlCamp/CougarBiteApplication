package com.capstone.kcamp.cougarbiteapplication.Model;

public class FoodCategory {
    private String text;
    private String image;

    public FoodCategory() {

    }

    public String getText() {
        return text;
    }

    public void setText(String name) {
        text = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public FoodCategory(String name, String image) {
        text = name;
        this.image = image;
    }
}
