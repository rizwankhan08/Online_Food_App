package com.model;

import java.sql.Timestamp;

/**
 * Review model for user reviews and ratings on restaurants/menu items.
 */
public class Review {

    private int id;
    private int userId;
    private int restaurantId;
    private int menuId;
    private int rating;
    private String comment;
    private Timestamp createdAt;

    // For display
    private String username;
    private String profileImage;

    // Default constructor
    public Review() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getRestaurantId() { return restaurantId; }
    public void setRestaurantId(int restaurantId) { this.restaurantId = restaurantId; }

    public int getMenuId() { return menuId; }
    public void setMenuId(int menuId) { this.menuId = menuId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }

    /**
     * Returns star icons for the rating (for JSP display).
     */
    public String getStarsHtml() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 5; i++) {
            if (i <= rating) {
                sb.append("★");
            } else {
                sb.append("☆");
            }
        }
        return sb.toString();
    }
}
