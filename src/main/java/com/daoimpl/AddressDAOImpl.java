package com.daoimpl;

import com.dao.AddressDAO;
import com.model.Address;
import com.util.DbConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddressDAOImpl implements AddressDAO {
    private static final Logger LOGGER = Logger.getLogger(AddressDAOImpl.class.getName());

    @Override
    public int addAddress(Address addr) {
        String sql = "INSERT INTO addresses(user_id,label,address_line,city,state,pincode,is_default) VALUES(?,?,?,?,?,?,?)";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, addr.getUserId()); ps.setString(2, addr.getLabel());
            ps.setString(3, addr.getAddressLine()); ps.setString(4, addr.getCity());
            ps.setString(5, addr.getState()); ps.setString(6, addr.getPincode());
            ps.setBoolean(7, addr.isDefault());
            return ps.executeUpdate();
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); return 0; }
    }

    @Override
    public int updateAddress(Address addr) {
        String sql = "UPDATE addresses SET label=?,address_line=?,city=?,state=?,pincode=? WHERE id=?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, addr.getLabel()); ps.setString(2, addr.getAddressLine());
            ps.setString(3, addr.getCity()); ps.setString(4, addr.getState());
            ps.setString(5, addr.getPincode()); ps.setInt(6, addr.getId());
            return ps.executeUpdate();
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); return 0; }
    }

    @Override
    public int deleteAddress(int addressId) {
        String sql = "DELETE FROM addresses WHERE id=?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, addressId); return ps.executeUpdate();
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); return 0; }
    }

    @Override
    public List<Address> getAddressesByUserId(int userId) {
        List<Address> list = new ArrayList<>();
        String sql = "SELECT * FROM addresses WHERE user_id=? ORDER BY is_default DESC, created_at DESC";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(mapAddress(rs)); }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return list;
    }

    @Override
    public Address getDefaultAddress(int userId) {
        String sql = "SELECT * FROM addresses WHERE user_id=? AND is_default=TRUE LIMIT 1";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return mapAddress(rs); }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return null;
    }

    @Override
    public int setDefaultAddress(int userId, int addressId) {
        try (Connection con = DbConnection.getConnection()) {
            try (PreparedStatement ps1 = con.prepareStatement("UPDATE addresses SET is_default=FALSE WHERE user_id=?")) {
                ps1.setInt(1, userId); ps1.executeUpdate();
            }
            try (PreparedStatement ps2 = con.prepareStatement("UPDATE addresses SET is_default=TRUE WHERE id=? AND user_id=?")) {
                ps2.setInt(1, addressId); ps2.setInt(2, userId); return ps2.executeUpdate();
            }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); return 0; }
    }

    @Override
    public Address getAddressById(int id) {
        String sql = "SELECT * FROM addresses WHERE id=?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return mapAddress(rs); }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return null;
    }

    private Address mapAddress(ResultSet rs) throws SQLException {
        Address a = new Address();
        a.setId(rs.getInt("id")); a.setUserId(rs.getInt("user_id"));
        a.setLabel(rs.getString("label")); a.setAddressLine(rs.getString("address_line"));
        a.setCity(rs.getString("city")); a.setState(rs.getString("state"));
        a.setPincode(rs.getString("pincode")); a.setDefault(rs.getBoolean("is_default"));
        a.setCreatedAt(rs.getTimestamp("created_at"));
        return a;
    }
}
