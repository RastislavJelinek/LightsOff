create database "lightsOff" with owner postgres;
create table comment
(
    ident        integer not null
        primary key,
    comment      varchar(255),
    commented_on timestamp,
    game         varchar(255),
    player       varchar(255)
);
create table rating
(
    ident    integer not null primary key,
    game     varchar(255),
    player   varchar(255),
    rated_on timestamp,
    rating   integer not null,
    constraint unique_rating unique (player, game)
);
create table score
(
    ident     integer not null
        primary key,
    game      varchar(255),
    played_on timestamp,
    player    varchar(255),
    points    integer not null
);