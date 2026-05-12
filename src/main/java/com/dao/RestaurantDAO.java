package com.dao;

import com.model.Restaurant;
import java.util.List;

/**
 * Data Access Object interface for Restaurant operations.
 */
public interface RestaurantDAO {

    /** Get all active restaurants */
    List<Restaurant> getAllRestaurants();

    /** Get restaurant by ID */
    Restaurant getRestaurantById(int id);

    /** Search restaurants by name or cuisine */
    List<Restaurant> searchRestaurants(String keyword);

    /** Filter restaurants by cuisine type */
    List<Restaurant> getRestaurantsByCuisine(String cuisine);

    /** Add a new restaurant (admin) */
    int addRestaurant(Restaurant restaurant);

    /** Update restaurant (admin) */
    int updateRestaurant(Restaurant restaurant);

    /** Delete restaurant (admin) */
    int deleteRestaurant(int id);

    /** Count total restaurants */
    int countRestaurants();

    /** Get all distinct cuisine types */
    List<String> getAllCuisineTypes();
}