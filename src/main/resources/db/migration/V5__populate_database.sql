INSERT INTO categories (name)
VALUES ('Fruits'),
       ('Dairy'),
       ('Bakery'),
       ('Snacks'),
       ('Beverages');

INSERT INTO products (name, price, description, category_id)
VALUES
-- Fruits
('Banana (1 Dozen)', 45.00, 'Fresh ripe bananas, perfect for snacking or smoothies.', 1),
('Apple (1 Kg)', 180.00, 'Juicy red apples sourced from Himachal Pradesh.', 1),

-- Dairy
('Amul Taaza Milk (1L)', 64.00, 'Pasteurized toned milk from Amul.', 2),
('Mother Dairy Curd (500g)', 30.00, 'Thick and creamy curd made from toned milk.', 2),

-- Bakery
('Harvest Gold Brown Bread', 40.00, 'Soft, wholesome bread with high fiber content.', 3),
('Britannia Fruit Cake (200g)', 65.00, 'Delicious sponge cake with real fruit bits.', 3),

-- Snacks
('Lay\'s Classic Salted Chips (52g)', 20.00, 'Crispy, salted potato chips for a quick snack.', 4),
('Kurkure Masala Munch (90g)', 25.00, 'Crunchy, spicy snack made with rice and corn.', 4),

-- Beverages
('Tropicana Orange Juice (1L)', 110.00, '100% pure orange juice with no added sugar.', 5),
('Bru Instant Coffee (100g)', 145.00, 'Rich and aromatic blend of coffee for a fresh start.', 5);


