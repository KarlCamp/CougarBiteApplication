package com.capstone.kcamp.cougarbiteapplication.Model;

public class FoodCategory {
    private String text;
    private String image;

    public FoodCategory() {

    }

    public String getName() {
        return text;
    }

    public void setName(String name) {
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
