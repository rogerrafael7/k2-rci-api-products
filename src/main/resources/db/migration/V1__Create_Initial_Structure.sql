create table products (
    id serial primary key,
    name varchar(255) not null,
    description text,
    price decimal(10, 2) not null,
    type varchar(255) not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
);