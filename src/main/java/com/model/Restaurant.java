package com.model;

import java.sql.Timestamp;

/**
 * Restaurant model representing a food establishment in the platform.
 */
public class Restaurant {

    private int id;
    private String name;
    private String description;
    private String location;
    private String cuisineType;
    private double rating;
    private String deliveryTime;
    private double minOrder;
    private boolean isActive;
    private String imageUrl;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Default constructor
    public Restaurant() {}

    // Basic constructor (backward compatible)
    public Restaurant(int id, String name, String location, double rating,
                      String deliveryTime, String imageUrl) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.imageUrl = imageUrl;
    }

    // Full constructor
    public Restaurant(int id, String name, String description, String location,
                      String cuisineType, double rating, String deliveryTime,
                      double minOrder, boolean isActive, String imageUrl,
                      Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.cuisineType = cuisineType;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.minOrder = minOrder;
        this.isActive = isActive;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getCuisineType() { return cuisineType; }
    public void setCuisineType(String cuisineType) { this.cuisineType = cuisineType; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getDeliveryTime() { return deliveryTime; }
    public void setDeliveryTime(String deliveryTime) { this.deliveryTime = deliveryTime; }

    public double getMinOrder() { return minOrder; }
    public void setMinOrder(double minOrder) { this.minOrder = minOrder; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}