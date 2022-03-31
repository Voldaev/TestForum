CREATE TABLE users
(
    id serial constraint user_id primary key ,
    name varchar(50) not null ,
    sign varchar(50) unique not null ,
    pass varchar(250) not null ,
    mail varchar(100) unique not null ,
    status int not null ,
    uuid varchar(250) ,
    avatar varchar(150)
);

CREATE TABLE sections
(
    name varchar(50) primary key
);
-- тестовый раздел
INSERT INTO sections (name) VALUES ('TestSection');

CREATE TABLE themes
(
    id serial constraint theme_id primary key ,
    section_name varchar(50) not null references sections(name) ,
    user_id int not null references users(id) ,
    theme varchar(150) not null unique,
    text varchar not null ,
    published timestamptz not null ,
    score int not null
);

CREATE TABLE comments
(
    id serial constraint comment_id primary key ,
    theme_id int not null references themes(id) ,
    user_id int not null references users(id) ,
    text varchar(200) not null ,
    published timestamptz not null ,
    score int not null
);
