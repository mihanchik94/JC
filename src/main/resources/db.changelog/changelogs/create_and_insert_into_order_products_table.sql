create table order_products (
    id bigserial primary key,
    order_id bigint references orders(order_id),
    product_id bigint references products(product_id)
);

insert into order_products (order_id, product_id) values
(1, 2),
(2, 2)
