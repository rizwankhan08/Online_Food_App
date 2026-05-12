package com.daoimpl;

import com.dao.UserDAO;
import com.model.User;
import com.util.DbConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAOImpl implements UserDAO {
    private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class.getName());

    @Override
    public int addUser(User user) {
        String sql = "INSERT INTO users(username,email,password,phone,address,role) VALUES(?,?,?,?,?,?)";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getUsername()); ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword()); ps.setString(4, user.getPhone());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getRole() != null ? user.getRole() : "customer");
            return ps.executeUpdate();
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error adding user", e); return 0; }
    }

    @Override
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return mapUser(rs); }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error getting user", e); }
        return null;
    }

    @Override
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return mapUser(rs); }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error getting user", e); }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return mapUser(rs); }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error getting user", e); }
        return null;
    }

    @Override
    public int updateUser(User user) {
        String sql = "UPDATE users SET username=?,email=?,phone=?,address=?,profile_image=? WHERE id=?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getUsername()); ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone()); ps.setString(4, user.getAddress());
            ps.setString(5, user.getProfileImage()); ps.setInt(6, user.getId());
            return ps.executeUpdate();
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error updating user", e); return 0; }
    }

    @Override
    public int updatePassword(int userId, String hashedPassword) {
        String sql = "UPDATE users SET password=? WHERE id=?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, hashedPassword); ps.setInt(2, userId);
            return ps.executeUpdate();
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error updating password", e); return 0; }
    }

    @Override
    public int deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id); return ps.executeUpdate();
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error deleting user", e); return 0; }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY created_at DESC";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) users.add(mapUser(rs));
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error getting all users", e); }
        return users;
    }

    @Override
    public int countUsers() {
        String sql = "SELECT COUNT(*) FROM users WHERE role='customer'";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error counting users", e); }
        return 0;
    }

    private User mapUser(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getInt("id")); u.setUsername(rs.getString("username"));
        u.setEmail(rs.getString("email")); u.setPassword(rs.getString("password"));
        u.setPhone(rs.getString("phone")); u.setAddress(rs.getString("address"));
        u.setProfileImage(rs.getString("profile_image")); u.setRole(rs.getString("role"));
        u.setActive(rs.getBoolean("is_active"));
        u.setCreatedAt(rs.getTimestamp("created_at")); u.setUpdatedAt(rs.getTimestamp("updated_at"));
        return u;
    }
}
