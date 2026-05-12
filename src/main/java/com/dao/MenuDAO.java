package com.dao;

import com.model.Menu;
import java.util.List;

/**
 * Data Access Object interface for Menu operations.
 */
public interface MenuDAO {

    /** Get all menu items for a restaurant */
    List<Menu> getMenuByRestaurantId(int restaurantId);

    /** Get a single menu item by ID */
    Menu getMenuById(int id);

    /** Search menu items by name */
    List<Menu> searchMenu(String keyword);

    /** Filter menu by category */
    List<Menu> getMenuByCategory(String category);

    /** Get menu items within price range */
    List<Menu> getMenuByPriceRange(double minPrice, double maxPrice);

    /** Add a new menu item (admin) */
    int addMenu(Menu menu);

    /** Update menu item (admin) */
    int updateMenu(Menu menu);

    /** Delete menu item (admin) */
    int deleteMenu(int id);

    /** Get all distinct categories */
    List<String> getAllCategories();

    /** Count total menu items */
    int countMenuItems();
}
