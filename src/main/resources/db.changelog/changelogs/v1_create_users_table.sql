create table users (
    id bigserial primary key,
    username varchar(128) not null unique,
    provider varchar(64) not null,
    provider_id varchar(128) not null unique
);