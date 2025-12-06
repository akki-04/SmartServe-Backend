create table user(
    id int primary key,
    name VARCHAR(50) not null,
    email VARCHAR(30) not null Unique,
    password VARCHAR(10) not null,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table accounts(
    ac_no Bigint primary key,
    email VARCHAR(30) not null Unique,
    name VARCHAR(50) not null,
    balance decimal(10,2) not null,
    security_pin VARCHAR(10)not null
);