create table users(
    id bigserial primary key,
    username varchar(128) not null unique,
    password varchar(128) not null ,
    is_account_non_locked boolean not null default true,
    failed_attempts int not null default 0
);