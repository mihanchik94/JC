create table books (
    id bigserial primary key,
    title varchar(128) not null,
    author varchar(128) not null,
    publication_year smallint
);

insert into books (title, author, publication_year) values
('Book 1', 'Author 1', 2021),
('Book 2', 'Author 2', 2022);