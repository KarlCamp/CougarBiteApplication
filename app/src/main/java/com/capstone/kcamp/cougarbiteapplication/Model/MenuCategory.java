package com.capstone.kcamp.cougarbiteapplication.Model;
public class MenuCategory {
    private String text;
    private String image;
    public MenuCategory() { }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public MenuCategory(String text, String image) {
        this.text = text;
        this.image = image;
    }
}