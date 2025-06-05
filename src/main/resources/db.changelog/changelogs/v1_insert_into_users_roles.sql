insert into users_roles(user_id, role_id) values
(1, (select id from roles where name = 'ROLE_USER')),
(2, (select id from roles where name = 'ROLE_MODERATOR')),
(3, (select id from roles where name = 'ROLE_SUPER_ADMIN'));