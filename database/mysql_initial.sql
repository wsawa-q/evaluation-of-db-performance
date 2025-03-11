-- Create a new database
CREATE DATABASE my_database;

-- Connect to the database
USE my_database;

-- Create a table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert initial data into the table
INSERT INTO users (username, email, password) VALUES
('john_doe', 'john@example.com', 'password123'),
('jane_smith', 'jane@example.com', 'securepassword'),
('alice', 'alice@example.com', 'alicepassword');

-- Create a second table for demonstration
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert initial data into the products table
INSERT INTO products (name, description, price) VALUES
('Laptop', 'A high-performance laptop.', 1200.50),
('Smartphone', 'A latest-generation smartphone.', 800.00),
('Headphones', 'Noise-canceling over-ear headphones.', 200.00);

-- Output a success message
SELECT 'Database and tables initialized successfully!' AS message;
