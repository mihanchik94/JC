create table departments (
    id bigserial primary key,
    name varchar(128) not null unique
);

insert into departments (name) values
('Human Resources'),
('Engineering'),
('Marketing');