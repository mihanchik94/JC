create table products (
    product_id bigserial primary key,
    name varchar(128) not null,
    description varchar(255),
    price decimal(10, 2),
    quantity_in_stock integer not null
);

insert into products (name, description, price, quantity_in_stock) values
('Laptop', 'A high-performance laptop', 999.99, 50),
('Smartphone', 'Latest model smartphone', 499.99, 100),
('Headphones', 'Noise-cancelling headphones', 199.99, 150);