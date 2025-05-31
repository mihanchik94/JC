CREATE TABLE employees (
    id bigserial primary key,
    first_name varchar(64) not null,
    last_name varchar(64) not null,
    position varchar(128) not null,
    salary decimal(15, 2) not null,
    department_id bigint references departments(id)
);

insert into employees (first_name, last_name, position, salary, department_id) values
('John', 'Doe', 'HR Manager', 75000.00, 1),
('Jane', 'Smith', 'Software Engineer', 90000.00, 2),
('Emily', 'Johnson', 'Marketing Specialist', 65000.00, 3),
('Michael', 'Brown', 'Senior Software Engineer', 110000.00, 2),
('Laura', 'Davis', 'Recruiter', 55000.00, 1);



