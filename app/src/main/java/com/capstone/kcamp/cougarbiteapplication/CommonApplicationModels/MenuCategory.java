package com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels;

/**
 * The MenuCategory class models the menu category model detailed in Firebase.
 * In other words, the class is reflective of the JSON object stored in the
 * database and properly parses object values by having identical parameter names.
 * Stores basic information about a category within the menu and helps populate
 * recycler view of MenuScreenViewHolder.
 *
 * @author Karl Camp
 * @version 1.0.0
 * @since 2019-05-04
 */
public class MenuCategory {
    private String name; //stores menu category name
    private String image; //stores menu category image (as a link to a public domain image)

    //generated appropriate setters and getters

    public String getText() {
        return name;
    }

    public void setText(String text) {
        this.name = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    //generated appropriate constructor initializing all class variables

    public MenuCategory(String text, String image) {
        this.name = text;
        this.image = image;
    }

    //generated appropriate default constructor

    public MenuCategory() {}
}