package com.daoimpl;

import com.dao.ReviewDAO;
import com.model.Review;
import com.util.DbConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReviewDAOImpl implements ReviewDAO {
    private static final Logger LOGGER = Logger.getLogger(ReviewDAOImpl.class.getName());

    @Override
    public int addReview(Review review) {
        String sql = "INSERT INTO reviews(user_id,restaurant_id,menu_id,rating,comment) VALUES(?,?,?,?,?)";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, review.getUserId());
            ps.setObject(2, review.getRestaurantId() > 0 ? review.getRestaurantId() : null);
            ps.setObject(3, review.getMenuId() > 0 ? review.getMenuId() : null);
            ps.setInt(4, review.getRating()); ps.setString(5, review.getComment());
            return ps.executeUpdate();
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error adding review", e); return 0; }
    }

    @Override
    public List<Review> getReviewsByRestaurantId(int restaurantId) {
        List<Review> list = new ArrayList<>();
        String sql = "SELECT r.*, u.username, u.profile_image FROM reviews r JOIN users u ON r.user_id=u.id WHERE r.restaurant_id=? ORDER BY r.created_at DESC";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, restaurantId);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(mapReview(rs)); }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return list;
    }

    @Override
    public List<Review> getReviewsByMenuId(int menuId) {
        List<Review> list = new ArrayList<>();
        String sql = "SELECT r.*, u.username, u.profile_image FROM reviews r JOIN users u ON r.user_id=u.id WHERE r.menu_id=? ORDER BY r.created_at DESC";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, menuId);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(mapReview(rs)); }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return list;
    }

    @Override
    public double getAverageRatingByRestaurant(int restaurantId) {
        String sql = "SELECT COALESCE(AVG(rating),0) FROM reviews WHERE restaurant_id=?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, restaurantId);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return rs.getDouble(1); }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return 0;
    }

    @Override
    public double getAverageRatingByMenu(int menuId) {
        String sql = "SELECT COALESCE(AVG(rating),0) FROM reviews WHERE menu_id=?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, menuId);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return rs.getDouble(1); }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return 0;
    }

    @Override
    public int deleteReview(int reviewId) {
        String sql = "DELETE FROM reviews WHERE id=?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, reviewId); return ps.executeUpdate();
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); return 0; }
    }

    @Override
    public List<Review> getReviewsByUserId(int userId) {
        List<Review> list = new ArrayList<>();
        String sql = "SELECT r.*, u.username, u.profile_image FROM reviews r JOIN users u ON r.user_id=u.id WHERE r.user_id=? ORDER BY r.created_at DESC";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(mapReview(rs)); }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return list;
    }

    private Review mapReview(ResultSet rs) throws SQLException {
        Review r = new Review();
        r.setId(rs.getInt("id")); r.setUserId(rs.getInt("user_id"));
        r.setRestaurantId(rs.getInt("restaurant_id")); r.setMenuId(rs.getInt("menu_id"));
        r.setRating(rs.getInt("rating")); r.setComment(rs.getString("comment"));
        r.setCreatedAt(rs.getTimestamp("created_at"));
        try { r.setUsername(rs.getString("username")); r.setProfileImage(rs.getString("profile_image")); } catch (SQLException ignored) {}
        return r;
    }
}
