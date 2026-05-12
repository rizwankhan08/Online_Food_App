package com.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

/**
 * Unit tests for DbConnection utility.
 */
class DbConnectionTest {

    @Test
    @DisplayName("Should obtain a valid connection from the pool")
    void testGetConnection() {
        assertDoesNotThrow(() -> {
            try (Connection con = DbConnection.getConnection()) {
                assertNotNull(con, "Connection should not be null");
                assertFalse(con.isClosed(), "Connection should be open");
            }
        });
    }

    @Test
    @DisplayName("Connection should be valid and auto-commit enabled")
    void testConnectionIsValid() {
        assertDoesNotThrow(() -> {
            try (Connection con = DbConnection.getConnection()) {
                assertTrue(con.isValid(5), "Connection should be valid within 5 seconds");
                assertTrue(con.getAutoCommit(), "Auto-commit should be enabled by default");
            }
        });
    }

    @Test
    @DisplayName("Should be able to get multiple connections from pool")
    void testMultipleConnections() {
        assertDoesNotThrow(() -> {
            try (Connection con1 = DbConnection.getConnection();
                 Connection con2 = DbConnection.getConnection()) {
                assertNotNull(con1);
                assertNotNull(con2);
                assertNotSame(con1, con2, "Should be different connection objects");
            }
        });
    }
}
