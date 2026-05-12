-- ============================================================
-- Online Food App - Complete Database Schema
-- Database: foodapp
-- ============================================================



-- ============================================================
-- 1. USERS TABLE
-- ============================================================
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(15),
    address TEXT,
    profile_image VARCHAR(255) DEFAULT 'images/default_avatar.png',
    role ENUM('customer', 'admin', 'delivery') DEFAULT 'customer',
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ============================================================
-- 2. ADDRESSES TABLE (Multiple addresses per user)
-- ============================================================
CREATE TABLE addresses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    label VARCHAR(50) DEFAULT 'Home',
    address_line TEXT NOT NULL,
    city VARCHAR(100),
    state VARCHAR(100),
    pincode VARCHAR(10),
    is_default BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ============================================================
-- 3. RESTAURANT TABLE
-- ============================================================
CREATE TABLE restaurant (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    location VARCHAR(200),
    cuisine_type VARCHAR(100),
    rating DECIMAL(2,1) DEFAULT 0.0,
    delivery_time VARCHAR(50),
    min_order DECIMAL(10,2) DEFAULT 0.00,
    is_active BOOLEAN DEFAULT TRUE,
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ============================================================
-- 4. MENU TABLE
-- ============================================================
CREATE TABLE menu (
    id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    category VARCHAR(50),
    rating DECIMAL(2,1) DEFAULT 0.0,
    is_veg BOOLEAN DEFAULT TRUE,
    is_available BOOLEAN DEFAULT TRUE,
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ============================================================
-- 5. ORDERS TABLE
-- ============================================================
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    restaurant_id INT,
    total_amount DECIMAL(10,2) NOT NULL,
    delivery_fee DECIMAL(10,2) DEFAULT 30.00,
    tax_amount DECIMAL(10,2) DEFAULT 0.00,
    grand_total DECIMAL(10,2) NOT NULL,
    delivery_address TEXT,
    payment_method ENUM('cod', 'upi', 'card', 'wallet') DEFAULT 'cod',
    payment_status ENUM('pending', 'paid', 'failed', 'refunded') DEFAULT 'pending',
    order_status ENUM('placed', 'confirmed', 'preparing', 'cooking', 'out_for_delivery', 'delivered', 'cancelled') DEFAULT 'placed',
    special_instructions TEXT,
    estimated_delivery VARCHAR(50),
    ordered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    delivered_at TIMESTAMP NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- ============================================================
-- 6. ORDER_ITEMS TABLE
-- ============================================================
CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    menu_id INT,
    item_name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    unit_price DECIMAL(10,2) NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (menu_id) REFERENCES menu(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- ============================================================
-- 7. REVIEWS TABLE
-- ============================================================
CREATE TABLE reviews (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    restaurant_id INT,
    menu_id INT,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id) ON DELETE CASCADE,
    FOREIGN KEY (menu_id) REFERENCES menu(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ============================================================
-- 8. FAVORITES TABLE
-- ============================================================
CREATE TABLE favorites (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    restaurant_id INT,
    menu_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id) ON DELETE CASCADE,
    FOREIGN KEY (menu_id) REFERENCES menu(id) ON DELETE CASCADE,
    UNIQUE KEY unique_fav_restaurant (user_id, restaurant_id),
    UNIQUE KEY unique_fav_menu (user_id, menu_id)
) ENGINE=InnoDB;

-- ============================================================
-- INDEXES FOR PERFORMANCE
-- ============================================================
CREATE INDEX idx_menu_restaurant ON menu(restaurant_id);
CREATE INDEX idx_orders_user ON orders(user_id);
CREATE INDEX idx_orders_status ON orders(order_status);
CREATE INDEX idx_order_items_order ON order_items(order_id);
CREATE INDEX idx_reviews_restaurant ON reviews(restaurant_id);
CREATE INDEX idx_reviews_menu ON reviews(menu_id);
CREATE INDEX idx_favorites_user ON favorites(user_id);

-- ============================================================
-- SAMPLE DATA
-- ============================================================

-- Admin User (password: Admin@123)
INSERT INTO users (username, email, password, phone, address, role) VALUES
('admin', 'admin@foodapp.com', '$2a$12$LJ3m4ys3Gzf0UOmSEAKHNOQgRNNPop.vHBwuOYG3GCfmlSbxPDsxe', '9999999999', 'Admin Office, Delhi', 'admin');

-- Sample Users (password: User@123)
INSERT INTO users (username, email, password, phone, address, role) VALUES
('john', 'john@email.com', '$2a$12$LJ3m4ys3Gzf0UOmSEAKHNOQgRNNPop.vHBwuOYG3GCfmlSbxPDsxe', '9876543210', '123 MG Road, Bangalore', 'customer'),
('priya', 'priya@email.com', '$2a$12$LJ3m4ys3Gzf0UOmSEAKHNOQgRNNPop.vHBwuOYG3GCfmlSbxPDsxe', '9876543211', '456 Park Street, Kolkata', 'customer'),
('rider1', 'rider1@email.com', '$2a$12$LJ3m4ys3Gzf0UOmSEAKHNOQgRNNPop.vHBwuOYG3GCfmlSbxPDsxe', '9876543212', 'Delivery Hub, Mumbai', 'delivery');

-- Sample Restaurants
INSERT INTO restaurant (name, description, location, cuisine_type, rating, delivery_time, min_order, image_url) VALUES
('Pizza Paradise', 'Authentic Italian pizzas made with fresh ingredients and wood-fired oven', 'Koramangala, Bangalore', 'Italian', 4.5, '25-35 min', 199.00, 'images/pizza.jpg'),
('Burger Barn', 'Gourmet burgers with premium patties and artisan buns', 'Indiranagar, Bangalore', 'American', 4.3, '20-30 min', 149.00, 'images/burger.jpg'),
('Biryani House', 'Royal Hyderabadi biryani with secret family recipes', 'HSR Layout, Bangalore', 'Indian', 4.7, '30-40 min', 249.00, 'images/biryani.jpg'),
('Dosa Corner', 'Crispy South Indian dosas with authentic chutneys', 'Jayanagar, Bangalore', 'South Indian', 4.4, '15-25 min', 99.00, 'images/dosa.jpg'),
('Noodle Wok', 'Indo-Chinese fusion noodles and stir-fries', 'Whitefield, Bangalore', 'Chinese', 4.2, '25-35 min', 149.00, 'images/noodles.jpg'),
('Paneer Palace', 'Premium North Indian paneer specialties', 'MG Road, Bangalore', 'North Indian', 4.6, '30-40 min', 199.00, 'images/paneer.jpg'),
('Tandoori Treats', 'Smoky tandoori dishes from the clay oven', 'BTM Layout, Bangalore', 'Mughlai', 4.5, '35-45 min', 249.00, 'images/tandoori.jpg'),
('Salad Studio', 'Fresh healthy salads and smoothie bowls', 'JP Nagar, Bangalore', 'Healthy', 4.1, '15-20 min', 149.00, 'images/salad.jpg'),
('Chaat Chowk', 'Authentic Indian street food and chaats', 'Marathahalli, Bangalore', 'Street Food', 4.3, '20-30 min', 99.00, 'images/chaat.jpg'),
('Cafe Latte', 'Premium coffee, pastries and light bites', 'Church Street, Bangalore', 'Cafe', 4.4, '15-25 min', 99.00, 'images/cafe.jpg');

-- Sample Menu Items
INSERT INTO menu (restaurant_id, name, description, price, category, rating, is_veg, image_url) VALUES
-- Pizza Paradise (id=1)
(1, 'Margherita Pizza', 'Classic pizza with mozzarella and fresh basil', 249.00, 'Pizza', 4.5, 1, 'images/menu/margherita_pizza.jpg'),
(1, 'Farmhouse Pizza', 'Loaded with fresh veggies and cheese', 349.00, 'Pizza', 4.6, 1, 'images/menu/farmhouse_pizza.jpg'),
-- Burger Barn (id=2)
(2, 'Cheese Burger', 'Juicy beef patty with melted cheddar', 199.00, 'Burger', 4.3, 0, 'images/menu/cheese_burger.jpg'),
(2, 'Double Patty Burger', 'Two patties with special sauce', 299.00, 'Burger', 4.5, 0, 'images/menu/double_patty_burger.jpg'),
-- Biryani House (id=3)
(3, 'Chicken Biryani', 'Aromatic basmati rice with tender chicken', 299.00, 'Biryani', 4.7, 0, 'images/menu/chicken_biryani.jpg'),
(3, 'Mutton Biryani', 'Slow-cooked mutton with fragrant spices', 399.00, 'Biryani', 4.8, 0, 'images/menu/mutton_biryani.jpg'),
-- Dosa Corner (id=4)
(4, 'Masala Dosa', 'Crispy dosa with spiced potato filling', 129.00, 'Dosa', 4.4, 1, 'images/menu/masala_dosa.jpg'),
(4, 'Plain Dosa', 'Classic crispy dosa with sambar and chutney', 99.00, 'Dosa', 4.2, 1, 'images/menu/plain_dosa.jpg'),
-- Noodle Wok (id=5)
(5, 'Veg Noodles', 'Stir-fried noodles with fresh vegetables', 179.00, 'Noodles', 4.1, 1, 'images/menu/veg_noodles.jpg'),
(5, 'Chicken Noodles', 'Spicy chicken hakka noodles', 229.00, 'Noodles', 4.3, 0, 'images/menu/chicken_noodles.jpg'),
-- Paneer Palace (id=6)
(6, 'Paneer Butter Masala', 'Creamy tomato gravy with soft paneer cubes', 259.00, 'Main Course', 4.6, 1, 'images/menu/paneer_butter_masala.jpg'),
(6, 'Paneer Tikka', 'Grilled paneer with spices and veggies', 229.00, 'Starter', 4.5, 1, 'images/menu/paneer_tikka.jpg');

-- Sample Addresses
INSERT INTO addresses (user_id, label, address_line, city, state, pincode, is_default) VALUES
(2, 'Home', '123 MG Road', 'Bangalore', 'Karnataka', '560001', TRUE),
(2, 'Office', '456 Tech Park', 'Bangalore', 'Karnataka', '560002', FALSE),
(3, 'Home', '456 Park Street', 'Kolkata', 'West Bengal', '700001', TRUE);

-- Sample Reviews
INSERT INTO reviews (user_id, restaurant_id, menu_id, rating, comment) VALUES
(2, 1, 1, 5, 'Best pizza in town! The crust is perfectly crispy.'),
(3, 3, 5, 4, 'Authentic biryani taste. Reminds me of home.'),
(2, 2, 3, 4, 'Great burgers! Cheese was melted perfectly.');

-- Sample Favorites
INSERT INTO favorites (user_id, restaurant_id) VALUES
(2, 1), (2, 3), (3, 2);

INSERT INTO favorites (user_id, menu_id) VALUES
(2, NULL), (3, NULL);
-- Fix favorites: add menu favorites properly
DELETE FROM favorites WHERE menu_id IS NULL AND restaurant_id IS NULL;
INSERT INTO favorites (user_id, menu_id) VALUES (2, 1), (3, 5);
