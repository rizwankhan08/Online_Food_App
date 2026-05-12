-- Clear existing data
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE order_items;
TRUNCATE TABLE orders;
TRUNCATE TABLE menu;
TRUNCATE TABLE restaurant;
SET FOREIGN_KEY_CHECKS = 1;

-- 1. Insert Restaurants
INSERT INTO restaurant (name, cuisine_type, rating, delivery_time, location, image_url) VALUES
('The Pizza Palace', 'Italian', 4.8, '25-35 min', '123 Italian St, Food City', 'images/res_pizza.jpg'),
('Burger Baron', 'American', 4.5, '20-30 min', '456 Grill Ave, Food City', 'images/res_burger.jpg'),
('Taj Mahal Delights', 'Indian', 4.9, '35-45 min', '789 Spice Rd, Food City', 'images/res_indian.jpg'),
('Dragon Wok', 'Chinese', 4.3, '30-40 min', '321 Orient Ln, Food City', 'images/res_chinese.jpg'),
('Sweet Retreat', 'Desserts', 4.7, '15-25 min', '654 Sugar Dr, Food City', 'images/res_dessert.jpg');

-- 2. Insert Menus
INSERT INTO menu (restaurant_id, name, description, price, category, is_veg, image_url) VALUES
(1, 'Margherita Pizza', 'Fresh basil and mozzarella.', 12.99, 'Main Course', 1, 'images/menu_margherita.jpg'),
(1, 'Pepperoni Feast', 'Double pepperoni and cheese.', 15.99, 'Main Course', 0, 'images/menu_pepperoni.jpg'),
(2, 'Classic Cheeseburger', 'Angus beef and cheddar.', 10.99, 'Fast Food', 0, 'images/menu_burger.jpg'),
(3, 'Butter Chicken', 'Rich tomato and butter gravy.', 14.99, 'Main Course', 0, 'images/menu_butter_chicken.jpg'),
(4, 'Vegetable Hakka Noodles', 'Stir-fried noodles with veggies.', 11.50, 'Main Course', 1, 'images/menu_noodles.jpg'),
(5, 'Chocolate Lava Cake', 'Warm cake with gooey center.', 8.99, 'Desserts', 1, 'images/menu_lavacake.jpg');
