create extension if not exists "uuid-ossp";

create table products (
    id uuid primary key default uuid_generate_v4(),
    name varchar(255) not null,
    description text,
    price decimal(10, 2) not null,
    type varchar(255) not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
);