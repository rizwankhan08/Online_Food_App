package com.model;

import java.sql.Timestamp;

/**
 * Favorite model for user's favorite restaurants and menu items.
 */
public class Favorite {

    private int id;
    private int userId;
    private int restaurantId;
    private int menuId;
    private Timestamp createdAt;

    // For display
    private String restaurantName;
    private String restaurantImage;
    private String menuName;
    private String menuImage;
    private double menuPrice;

    // Default constructor
    public Favorite() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getRestaurantId() { return restaurantId; }
    public void setRestaurantId(int restaurantId) { this.restaurantId = restaurantId; }

    public int getMenuId() { return menuId; }
    public void setMenuId(int menuId) { this.menuId = menuId; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getRestaurantName() { return restaurantName; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

    public String getRestaurantImage() { return restaurantImage; }
    public void setRestaurantImage(String restaurantImage) { this.restaurantImage = restaurantImage; }

    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }

    public String getMenuImage() { return menuImage; }
    public void setMenuImage(String menuImage) { this.menuImage = menuImage; }

    public double getMenuPrice() { return menuPrice; }
    public void setMenuPrice(double menuPrice) { this.menuPrice = menuPrice; }
}
