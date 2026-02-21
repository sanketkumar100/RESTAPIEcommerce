CREATE TABLE users (
                       id BIGINT IDENTITY(1,1) PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE categories (
                            id TINYINT IDENTITY(1,1) PRIMARY KEY,
                            name VARCHAR(255) NOT NULL
);

CREATE TABLE products (
                          id BIGINT IDENTITY(1,1) PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          price DECIMAL(10,2) NOT NULL,
                          description VARCHAR(MAX) NOT NULL,
    category_id TINYINT NULL,
    CONSTRAINT FK_products_category
        FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE addresses (
                           id BIGINT IDENTITY(1,1) PRIMARY KEY,
                           street VARCHAR(255) NOT NULL,
                           city VARCHAR(255) NOT NULL,
                           state VARCHAR(255) NOT NULL,
                           zip VARCHAR(255) NOT NULL,
                           user_id BIGINT NOT NULL,
                           CONSTRAINT FK_addresses_user
                               FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE profiles (
                          id BIGINT PRIMARY KEY,
                          bio VARCHAR(MAX) NULL,
    phone_number VARCHAR(15) NULL,
    date_of_birth DATE NULL,
    loyalty_points INT DEFAULT 0,
    CONSTRAINT FK_profiles_user
        FOREIGN KEY (id) REFERENCES users(id)
);

CREATE TABLE wishlist (
                          product_id BIGINT NOT NULL,
                          user_id BIGINT NOT NULL,
                          PRIMARY KEY (product_id, user_id),
                          CONSTRAINT FK_wishlist_product
                              FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
                          CONSTRAINT FK_wishlist_user
                              FOREIGN KEY (user_id) REFERENCES users(id)
);
