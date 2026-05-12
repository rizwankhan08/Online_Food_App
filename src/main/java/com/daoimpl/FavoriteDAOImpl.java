package com.daoimpl;

import com.dao.FavoriteDAO;
import com.model.Favorite;
import com.util.DbConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FavoriteDAOImpl implements FavoriteDAO {
    private static final Logger LOGGER = Logger.getLogger(FavoriteDAOImpl.class.getName());

    @Override
    public int addFavoriteRestaurant(int userId, int restaurantId) {
        String sql = "INSERT IGNORE INTO favorites(user_id,restaurant_id) VALUES(?,?)";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId); ps.setInt(2, restaurantId); return ps.executeUpdate();
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); return 0; }
    }

    @Override
    public int addFavoriteMenu(int userId, int menuId) {
        String sql = "INSERT IGNORE INTO favorites(user_id,menu_id) VALUES(?,?)";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId); ps.setInt(2, menuId); return ps.executeUpdate();
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); return 0; }
    }

    @Override
    public int removeFavorite(int favoriteId) {
        String sql = "DELETE FROM favorites WHERE id=?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, favoriteId); return ps.executeUpdate();
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); return 0; }
    }

    @Override
    public int removeFavoriteRestaurant(int userId, int restaurantId) {
        String sql = "DELETE FROM favorites WHERE user_id=? AND restaurant_id=?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId); ps.setInt(2, restaurantId); return ps.executeUpdate();
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); return 0; }
    }

    @Override
    public int removeFavoriteMenu(int userId, int menuId) {
        String sql = "DELETE FROM favorites WHERE user_id=? AND menu_id=?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId); ps.setInt(2, menuId); return ps.executeUpdate();
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); return 0; }
    }

    @Override
    public List<Favorite> getFavoriteRestaurants(int userId) {
        List<Favorite> list = new ArrayList<>();
        String sql = "SELECT f.*, r.name as restaurant_name, r.image_url as restaurant_image FROM favorites f JOIN restaurant r ON f.restaurant_id=r.id WHERE f.user_id=? AND f.restaurant_id IS NOT NULL";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Favorite fav = new Favorite();
                    fav.setId(rs.getInt("id")); fav.setUserId(rs.getInt("user_id"));
                    fav.setRestaurantId(rs.getInt("restaurant_id")); fav.setCreatedAt(rs.getTimestamp("created_at"));
                    fav.setRestaurantName(rs.getString("restaurant_name")); fav.setRestaurantImage(rs.getString("restaurant_image"));
                    list.add(fav);
                }
            }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return list;
    }

    @Override
    public List<Favorite> getFavoriteMenuItems(int userId) {
        List<Favorite> list = new ArrayList<>();
        String sql = "SELECT f.*, m.name as menu_name, m.image_url as menu_image, m.price as menu_price FROM favorites f JOIN menu m ON f.menu_id=m.id WHERE f.user_id=? AND f.menu_id IS NOT NULL";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Favorite fav = new Favorite();
                    fav.setId(rs.getInt("id")); fav.setUserId(rs.getInt("user_id"));
                    fav.setMenuId(rs.getInt("menu_id")); fav.setCreatedAt(rs.getTimestamp("created_at"));
                    fav.setMenuName(rs.getString("menu_name")); fav.setMenuImage(rs.getString("menu_image"));
                    fav.setMenuPrice(rs.getDouble("menu_price"));
                    list.add(fav);
                }
            }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return list;
    }

    @Override
    public boolean isRestaurantFavorited(int userId, int restaurantId) {
        String sql = "SELECT COUNT(*) FROM favorites WHERE user_id=? AND restaurant_id=?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId); ps.setInt(2, restaurantId);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return rs.getInt(1) > 0; }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return false;
    }

    @Override
    public boolean isMenuFavorited(int userId, int menuId) {
        String sql = "SELECT COUNT(*) FROM favorites WHERE user_id=? AND menu_id=?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId); ps.setInt(2, menuId);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return rs.getInt(1) > 0; }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return false;
    }
}
