# 🍕 FoodApp - Professional Food Delivery Platform

A robust, full-stack food delivery web application built using **Java (Jakarta EE)**, **MVC Architecture**, and **MySQL**. This project demonstrates industry-standard practices including connection pooling, secure authentication, and third-party API integration.

## 🚀 Key Features

- **User Authentication**: Secure login/registration with **BCrypt** password hashing.
- **Dynamic Restaurant Catalog**: Search and filter restaurants by cuisine and rating.
- **Advanced Cart System**: Real-time cart updates with session management.
- **Payment Gateway**: Integrated **Razorpay** for secure UPI, Card, and Netbanking payments.
- **Admin Dashboard**: Comprehensive panel for managing orders, inventory, and users.
- **Performance**: High-performance database operations using **HikariCP** Connection Pooling.
- **Mobile Responsive**: Modern, mobile-first UI designed with vanilla CSS.

## 🛠️ Tech Stack

- **Backend**: Java 21, Jakarta Servlets, JSP
- **Build Tool**: Maven
- **Database**: MySQL 8.0 (with HikariCP Pooling)
- **Security**: BCrypt Hashing, Jakarta Auth Filters
- **Frontend**: HTML5, CSS3 (Glassmorphism design), JavaScript (ES6+)
- **Integration**: Razorpay SDK

## 🏗️ Architecture (MVC)

The project follows the **Model-View-Controller** pattern for clean separation of concerns:
- **Model**: POJOs for User, Order, Restaurant, etc.
- **View**: Dynamic JSPs with modern UI/UX.
- **Controller**: Specialized Servlets for business logic handling.
- **DAO Layer**: Robust Data Access Objects for database abstraction.

## 🔧 Installation & Setup

1. **Clone the repo**:
   ```bash
   git clone https://github.com/rizwankhan08/Online-Food-App.git
   ```
2. **Database Setup**:
   - Execute the SQL script provided in `/sql/schema.sql`.
   - Update `DbConnection.java` with your MySQL credentials.
3. **Maven Build**:
   - Right-click project -> Maven -> Update Project.
4. **Deploy**:
   - Deploy to **Apache Tomcat 10.1+**.

## 📞 Contact
**Rizwan Khan** - [LinkedIn](https://linkedin.com/in/rizwankhan-dev) - rizwaankhan10145@gmail.com
