package com.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database connection utility using HikariCP Connection Pool.
 * <p>
 * HikariCP maintains a pool of reusable connections, dramatically improving
 * performance over opening a new connection for every request.
 * </p>
 */
public class DbConnection {

    private static final Logger LOGGER = Logger.getLogger(DbConnection.class.getName());
    private static final HikariDataSource dataSource;

    private static String getJdbcUrl() {
        String envUrl = System.getenv("DB_URL");
        if (envUrl != null) {
            // Convert Railway/Heroku style mysql:// to jdbc:mysql://
            if (envUrl.startsWith("mysql://")) {
                return "jdbc:" + envUrl;
            }
            return envUrl;
        }
        return "jdbc:mysql://localhost:3306/foodapp?useSSL=false&serverTimezone=Asia/Kolkata&allowPublicKeyRetrieval=true";
    }

    private static final String URL = getJdbcUrl();
    
    private static final String USERNAME = System.getenv("DB_USER") != null ? 
            System.getenv("DB_USER") : "root";
            
    private static final String PASSWORD = System.getenv("DB_PASSWORD") != null ? 
            System.getenv("DB_PASSWORD") : "Rizwan@08";

    // Initialize connection pool at class loading time
    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(URL);
            config.setUsername(USERNAME);
            config.setPassword(PASSWORD);
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");

            // Pool tuning
            config.setMaximumPoolSize(20);          // Max connections in pool
            config.setMinimumIdle(5);               // Min idle connections kept alive
            config.setIdleTimeout(300000);           // 5 min idle timeout
            config.setConnectionTimeout(20000);     // 20 sec max wait for connection
            config.setMaxLifetime(1200000);          // 20 min max connection lifetime
            config.setLeakDetectionThreshold(60000); // Log warning if connection not returned in 60s

            // Performance optimizations
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.addDataSourceProperty("useServerPrepStmts", "true");

            config.setPoolName("FoodApp-Pool");

            dataSource = new HikariDataSource(config);
            LOGGER.info("HikariCP connection pool initialized successfully. Pool: " + config.getPoolName());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize HikariCP connection pool", e);
            throw new RuntimeException("Failed to initialize database connection pool", e);
        }
    }

    /**
     * Returns a connection from the HikariCP pool.
     * Caller MUST close the connection (use try-with-resources).
     * The connection is automatically returned to the pool when closed.
     *
     * @return Connection object from the pool
     * @throws SQLException if no connection is available
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Legacy method for backward compatibility.
     * @deprecated Use {@link #getConnection()} instead.
     */
    @Deprecated
    public static Connection getDbConnection() {
        try {
            return getConnection();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection failed", e);
            return null;
        }
    }

    /**
     * Closes a connection safely (returns it to the pool).
     * @param con the connection to close
     */
    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close(); // Returns to pool, doesn't actually close
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error closing connection", e);
            }
        }
    }

    /**
     * Shuts down the connection pool. Call this on application shutdown.
     */
    public static void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            LOGGER.info("HikariCP connection pool shut down.");
        }
    }
}
