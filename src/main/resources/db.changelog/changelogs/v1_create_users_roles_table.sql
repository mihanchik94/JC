create table users_roles(
    id bigserial primary key,
    user_id bigint references users(id),
    role_id bigint references roles(id)
);