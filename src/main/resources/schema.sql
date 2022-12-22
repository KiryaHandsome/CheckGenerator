DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS discount_card;

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    price DOUBLE PRECISION,
    is_promotional BOOLEAN
);

CREATE TABLE discount_card (
    id SERIAL PRIMARY KEY,
    discount DOUBLE PRECISION
);