create table topic
(
    id    int identity
        constraint topic_pk
            primary key,
    title varchar(255)
)
go

create table users
(
    id           int identity
        constraint users_pk
            primary key,
    username     varchar(50)  not null,
    password     varchar(100) not null,
    email        varchar(100) not null,
    first_name   varchar(100),
    last_name    varchar(100),
    user_level   tinyint,
    phone_number varchar(100),
    avatar       varchar(255)
)
go

create table posts
(
    id            int identity
        constraint posts_pk
            primary key,
    creator       int
        constraint posts_users_id_fk
            references users,
    likes         int,
    dislikes      int,
    title         varchar(255) not null,
    content       varchar(max) not null,
    views         int,
    creation_date datetime     not null,
    last_activity datetime     not null,
    comment_count int,
    comment_list   int,
    topic         int
        constraint posts_topic_id_fk
            references topic
)
go

create table tags
(
    id         int identity
        constraint tags_pk
            primary key,
    name       varchar(max) not null,
    belongs_to int
        constraint tags_posts_id_fk
            references posts
)
go


