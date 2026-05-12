package com.listener;

import com.util.DbConnection;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.logging.Logger;

/**
 * Application lifecycle listener.
 * Shuts down the HikariCP connection pool when the application stops.
 * This prevents connection leaks during server restart/shutdown.
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(AppContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("FoodApp started successfully.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("FoodApp shutting down. Closing connection pool...");
        DbConnection.shutdown();
        LOGGER.info("Connection pool closed. Goodbye!");
    }
}
