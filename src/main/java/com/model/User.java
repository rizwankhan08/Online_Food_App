package com.model;

import java.sql.Timestamp;

/**
 * User model representing a registered user in the food delivery system.
 * Supports customer, admin, and delivery partner roles.
 */
public class User {

    private int id;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String profileImage;
    private String role;
    private boolean isActive;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Default constructor
    public User() {}

    // Basic constructor for registration
    public User(String username, String password, String email, String address, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.role = role;
    }

    // Full constructor
    public User(int id, String username, String email, String password, String phone,
                String address, String profileImage, String role, boolean isActive,
                Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.profileImage = profileImage;
        this.role = role;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public boolean isAdmin() { return "admin".equalsIgnoreCase(this.role); }
    public boolean isCustomer() { return "customer".equalsIgnoreCase(this.role); }
    public boolean isDelivery() { return "delivery".equalsIgnoreCase(this.role); }
}
