CREATE TABLE users
(
    id serial constraint user_id primary key ,
    name varchar(50) not null ,
    sign varchar(50) unique not null ,
    pass varchar(250) not null ,
    mail varchar(100) unique not null ,
    status int not null ,
    uuid varchar(250)
);