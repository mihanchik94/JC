create table orders (
    order_id bigserial primary key,
    order_date timestamp default current_timestamp,
    shipping_address varchar(255) not null,
    total_price decimal(10, 2) not null,
    order_status varchar(32),
    customer_id bigint references customers(customer_id)
);

insert into orders (order_date, shipping_address, total_price, order_status, customer_id) values
('2023-10-01 10:00:00', '123 Main St, Anytown, USA', 1499.98, 'COMPLETED', 1),
('2023-10-02 12:30:00', '456 Elm St, Othertown, USA', 699.98, 'IN_PROGRESS', 2);