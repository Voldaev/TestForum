CREATE TABLE users
(
    id serial constraint user_id primary key ,
    name varchar(50) not null ,
    sign varchar(50) unique not null ,
    pass varchar(250) not null ,
    mail varchar(100) unique not null ,
    status varchar(10) not null ,
    uuid varchar(250) ,
    avatar varchar(150)
);

CREATE TABLE sections
(
    name varchar(50) primary key
);

CREATE TABLE themes
(
    id serial constraint theme_id primary key ,
    section_name varchar(50) not null references sections(name) ,
    user_id int not null references users(id) ,
    theme varchar(150) not null unique,
    text varchar not null ,
    published timestamptz not null
);

CREATE TABLE comments
(
    id serial constraint comment_id primary key ,
    theme_id int not null references themes(id) ,
    user_id int not null references users(id) ,
    text varchar(200) not null ,
    published timestamptz not null
);

CREATE TABLE votes_t
(
    id serial constraint vote_t_id primary key ,
    user_id int not null references users(id) ,
    theme_id int not null references themes(id)
);

CREATE TABLE votes_c
(
    id serial constraint vote_c_id primary key ,
    user_id int not null references users(id) ,
    comm_id int not null references comments(id)
);
