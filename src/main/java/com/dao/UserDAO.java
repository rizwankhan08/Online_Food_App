package com.dao;

import com.model.User;
import java.util.List;

/**
 * Data Access Object interface for User operations.
 */
public interface UserDAO {

    /** Register a new user */
    int addUser(User user);

    /** Get user by username for login */
    User getUserByUsername(String username);

    /** Get user by ID */
    User getUserById(int id);

    /** Get user by email */
    User getUserByEmail(String email);

    /** Update user profile */
    int updateUser(User user);

    /** Update user password */
    int updatePassword(int userId, String hashedPassword);

    /** Delete user */
    int deleteUser(int id);

    /** Get all users (admin) */
    List<User> getAllUsers();

    /** Count total users */
    int countUsers();
}