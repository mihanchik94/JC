create table orders(
order_id bigserial primary key,
products text,
order_status varchar(32)
);