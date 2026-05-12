package com.model;

import java.sql.Timestamp;

/**
 * Menu model representing a food item in a restaurant's menu.
 */
public class Menu {

    private int id;
    private int restaurantId;
    private String name;
    private String description;
    private double price;
    private String category;
    private double rating;
    private boolean isVeg;
    private boolean isAvailable;
    private String imageUrl;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Default constructor
    public Menu() {}

    // Basic constructor (backward compatible)
    public Menu(int id, int restaurantId, String name, String description,
                double price, double rating, String imageUrl) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.imageUrl = imageUrl;
    }

    // Full constructor
    public Menu(int id, int restaurantId, String name, String description,
                double price, String category, double rating, boolean isVeg,
                boolean isAvailable, String imageUrl, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.rating = rating;
        this.isVeg = isVeg;
        this.isAvailable = isAvailable;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getRestaurantId() { return restaurantId; }
    public void setRestaurantId(int restaurantId) { this.restaurantId = restaurantId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public boolean isVeg() { return isVeg; }
    public void setVeg(boolean veg) { isVeg = veg; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}