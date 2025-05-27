create table authors(
    id bigserial primary key,
    name varchar(128) not null unique
);

insert into authors(name) values
('Лев Толстой'),
('Федор Достоевский'),
('Антон Чехов'),
('Лев Гумилёв'),
('Борис Пастернак'),
('Александр Солженицын'),
('Михаил Булгаков'),
('Иван Тургенев'),
('Николай Гоголь'),
('Александр Пушкин');