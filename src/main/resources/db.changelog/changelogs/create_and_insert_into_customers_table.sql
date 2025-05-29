create table customers (
    customer_id bigserial primary key,
    first_name varchar(64) not null,
    last_name varchar(64) not null,
    email varchar(64) not null unique ,
    contact_number varchar(64) not null unique
);

insert into customers (first_name, last_name, email, contact_number) values
('John', 'Doe', 'john.doe@example.com', '123-456-7890'),
('Jane', 'Smith', 'jane.smith@example.com', '098-765-4321');