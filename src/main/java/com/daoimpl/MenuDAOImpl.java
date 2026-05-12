package com.daoimpl;

import com.dao.MenuDAO;
import com.model.Menu;
import com.util.DbConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuDAOImpl implements MenuDAO {
    private static final Logger LOGGER = Logger.getLogger(MenuDAOImpl.class.getName());

    @Override
    public List<Menu> getMenuByRestaurantId(int restaurantId) {
        List<Menu> list = new ArrayList<>();
        String sql = "SELECT * FROM menu WHERE restaurant_id = ? AND is_available = TRUE ORDER BY category, name";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, restaurantId);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(mapMenu(rs)); }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return list;
    }

    @Override
    public Menu getMenuById(int id) {
        String sql = "SELECT * FROM menu WHERE id = ?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return mapMenu(rs); }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return null;
    }

    @Override
    public List<Menu> searchMenu(String keyword) {
        List<Menu> list = new ArrayList<>();
        String sql = "SELECT * FROM menu WHERE is_available=TRUE AND (name LIKE ? OR description LIKE ? OR category LIKE ?) ORDER BY rating DESC";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            String s = "%" + keyword + "%"; ps.setString(1, s); ps.setString(2, s); ps.setString(3, s);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(mapMenu(rs)); }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return list;
    }

    @Override
    public List<Menu> getMenuByCategory(String category) {
        List<Menu> list = new ArrayList<>();
        String sql = "SELECT * FROM menu WHERE is_available=TRUE AND category=? ORDER BY rating DESC";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, category);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(mapMenu(rs)); }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return list;
    }

    @Override
    public List<Menu> getMenuByPriceRange(double min, double max) {
        List<Menu> list = new ArrayList<>();
        String sql = "SELECT * FROM menu WHERE is_available=TRUE AND price BETWEEN ? AND ? ORDER BY price";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, min); ps.setDouble(2, max);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(mapMenu(rs)); }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return list;
    }

    @Override
    public int addMenu(Menu m) {
        String sql = "INSERT INTO menu(restaurant_id,name,description,price,category,rating,is_veg,image_url) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1,m.getRestaurantId()); ps.setString(2,m.getName()); ps.setString(3,m.getDescription());
            ps.setDouble(4,m.getPrice()); ps.setString(5,m.getCategory()); ps.setDouble(6,m.getRating());
            ps.setBoolean(7,m.isVeg()); ps.setString(8,m.getImageUrl());
            return ps.executeUpdate();
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); return 0; }
    }

    @Override
    public int updateMenu(Menu m) {
        String sql = "UPDATE menu SET restaurant_id=?,name=?,description=?,price=?,category=?,rating=?,is_veg=?,is_available=?,image_url=? WHERE id=?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1,m.getRestaurantId()); ps.setString(2,m.getName()); ps.setString(3,m.getDescription());
            ps.setDouble(4,m.getPrice()); ps.setString(5,m.getCategory()); ps.setDouble(6,m.getRating());
            ps.setBoolean(7,m.isVeg()); ps.setBoolean(8,m.isAvailable()); ps.setString(9,m.getImageUrl()); ps.setInt(10,m.getId());
            return ps.executeUpdate();
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); return 0; }
    }

    @Override
    public int deleteMenu(int id) {
        String sql = "DELETE FROM menu WHERE id = ?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id); return ps.executeUpdate();
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); return 0; }
    }

    @Override
    public List<String> getAllCategories() {
        List<String> cats = new ArrayList<>();
        String sql = "SELECT DISTINCT category FROM menu WHERE category IS NOT NULL ORDER BY category";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) cats.add(rs.getString("category"));
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return cats;
    }

    @Override
    public int countMenuItems() {
        String sql = "SELECT COUNT(*) FROM menu";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return 0;
    }

    private Menu mapMenu(ResultSet rs) throws SQLException {
        Menu m = new Menu(); m.setId(rs.getInt("id")); m.setRestaurantId(rs.getInt("restaurant_id"));
        m.setName(rs.getString("name")); m.setDescription(rs.getString("description"));
        m.setPrice(rs.getDouble("price")); m.setCategory(rs.getString("category"));
        m.setRating(rs.getDouble("rating")); m.setVeg(rs.getBoolean("is_veg"));
        m.setAvailable(rs.getBoolean("is_available")); m.setImageUrl(rs.getString("image_url"));
        m.setCreatedAt(rs.getTimestamp("created_at")); m.setUpdatedAt(rs.getTimestamp("updated_at"));
        return m;
    }
}
