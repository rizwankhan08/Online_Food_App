package com.daoimpl;

import com.dao.RestaurantDAO;
import com.model.Restaurant;
import com.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of RestaurantDAO interface.
 */
public class RestaurantDAOImpl implements RestaurantDAO {

    private static final Logger LOGGER = Logger.getLogger(RestaurantDAOImpl.class.getName());

    @Override
    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> list = new ArrayList<>();
        String sql = "SELECT * FROM restaurant WHERE is_active = TRUE ORDER BY rating DESC";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRestaurant(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting all restaurants", e);
        }
        return list;
    }

    @Override
    public Restaurant getRestaurantById(int id) {
        String sql = "SELECT * FROM restaurant WHERE id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRestaurant(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting restaurant by ID: " + id, e);
        }
        return null;
    }

    @Override
    public List<Restaurant> searchRestaurants(String keyword) {
        List<Restaurant> list = new ArrayList<>();
        String sql = "SELECT * FROM restaurant WHERE is_active = TRUE AND (name LIKE ? OR cuisine_type LIKE ? OR location LIKE ?) ORDER BY rating DESC";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String search = "%" + keyword + "%";
            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRestaurant(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error searching restaurants: " + keyword, e);
        }
        return list;
    }

    @Override
    public List<Restaurant> getRestaurantsByCuisine(String cuisine) {
        List<Restaurant> list = new ArrayList<>();
        String sql = "SELECT * FROM restaurant WHERE is_active = TRUE AND cuisine_type = ? ORDER BY rating DESC";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cuisine);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRestaurant(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting restaurants by cuisine: " + cuisine, e);
        }
        return list;
    }

    @Override
    public int addRestaurant(Restaurant restaurant) {
        String sql = "INSERT INTO restaurant(name, description, location, cuisine_type, rating, delivery_time, min_order, image_url) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, restaurant.getName());
            ps.setString(2, restaurant.getDescription());
            ps.setString(3, restaurant.getLocation());
            ps.setString(4, restaurant.getCuisineType());
            ps.setDouble(5, restaurant.getRating());
            ps.setString(6, restaurant.getDeliveryTime());
            ps.setDouble(7, restaurant.getMinOrder());
            ps.setString(8, restaurant.getImageUrl());

            return ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding restaurant", e);
            return 0;
        }
    }

    @Override
    public int updateRestaurant(Restaurant restaurant) {
        String sql = "UPDATE restaurant SET name=?, description=?, location=?, cuisine_type=?, rating=?, delivery_time=?, min_order=?, image_url=?, is_active=? WHERE id=?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, restaurant.getName());
            ps.setString(2, restaurant.getDescription());
            ps.setString(3, restaurant.getLocation());
            ps.setString(4, restaurant.getCuisineType());
            ps.setDouble(5, restaurant.getRating());
            ps.setString(6, restaurant.getDeliveryTime());
            ps.setDouble(7, restaurant.getMinOrder());
            ps.setString(8, restaurant.getImageUrl());
            ps.setBoolean(9, restaurant.isActive());
            ps.setInt(10, restaurant.getId());

            return ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating restaurant: " + restaurant.getId(), e);
            return 0;
        }
    }

    @Override
    public int deleteRestaurant(int id) {
        String sql = "DELETE FROM restaurant WHERE id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting restaurant: " + id, e);
            return 0;
        }
    }

    @Override
    public int countRestaurants() {
        String sql = "SELECT COUNT(*) FROM restaurant WHERE is_active = TRUE";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error counting restaurants", e);
        }
        return 0;
    }

    @Override
    public List<String> getAllCuisineTypes() {
        List<String> cuisines = new ArrayList<>();
        String sql = "SELECT DISTINCT cuisine_type FROM restaurant WHERE cuisine_type IS NOT NULL ORDER BY cuisine_type";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                cuisines.add(rs.getString("cuisine_type"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting cuisine types", e);
        }
        return cuisines;
    }

    private Restaurant mapRestaurant(ResultSet rs) throws SQLException {
        Restaurant r = new Restaurant();
        r.setId(rs.getInt("id"));
        r.setName(rs.getString("name"));
        r.setDescription(rs.getString("description"));
        r.setLocation(rs.getString("location"));
        r.setCuisineType(rs.getString("cuisine_type"));
        r.setRating(rs.getDouble("rating"));
        r.setDeliveryTime(rs.getString("delivery_time"));
        r.setMinOrder(rs.getDouble("min_order"));
        r.setActive(rs.getBoolean("is_active"));
        r.setImageUrl(rs.getString("image_url"));
        r.setCreatedAt(rs.getTimestamp("created_at"));
        r.setUpdatedAt(rs.getTimestamp("updated_at"));
        return r;
    }
}
