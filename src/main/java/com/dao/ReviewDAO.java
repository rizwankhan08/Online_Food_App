package com.dao;

import com.model.Review;
import java.util.List;

/**
 * Data Access Object interface for Review operations.
 */
public interface ReviewDAO {

    /** Add a new review */
    int addReview(Review review);

    /** Get reviews for a restaurant */
    List<Review> getReviewsByRestaurantId(int restaurantId);

    /** Get reviews for a menu item */
    List<Review> getReviewsByMenuId(int menuId);

    /** Get average rating for a restaurant */
    double getAverageRatingByRestaurant(int restaurantId);

    /** Get average rating for a menu item */
    double getAverageRatingByMenu(int menuId);

    /** Delete a review */
    int deleteReview(int reviewId);

    /** Get reviews by user */
    List<Review> getReviewsByUserId(int userId);
}
