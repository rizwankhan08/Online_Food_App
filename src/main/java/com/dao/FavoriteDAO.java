package com.dao;

import com.model.Favorite;
import java.util.List;

/**
 * Data Access Object interface for Favorite/Wishlist operations.
 */
public interface FavoriteDAO {

    /** Add restaurant to favorites */
    int addFavoriteRestaurant(int userId, int restaurantId);

    /** Add menu item to favorites */
    int addFavoriteMenu(int userId, int menuId);

    /** Remove a favorite */
    int removeFavorite(int favoriteId);

    /** Remove restaurant from favorites */
    int removeFavoriteRestaurant(int userId, int restaurantId);

    /** Remove menu item from favorites */
    int removeFavoriteMenu(int userId, int menuId);

    /** Get user's favorite restaurants */
    List<Favorite> getFavoriteRestaurants(int userId);

    /** Get user's favorite menu items */
    List<Favorite> getFavoriteMenuItems(int userId);

    /** Check if restaurant is favorited by user */
    boolean isRestaurantFavorited(int userId, int restaurantId);

    /** Check if menu item is favorited by user */
    boolean isMenuFavorited(int userId, int menuId);
}
