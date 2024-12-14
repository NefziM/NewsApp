package com.example.news_app;

public class Category {

    private String name;
    private int imageResource;

    // Constructor
    public Category(String name, int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for imageResource
    public int getImageResource() {
        return imageResource;
    }
}
