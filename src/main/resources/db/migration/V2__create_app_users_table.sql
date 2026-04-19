create table app_users (
    id uuid not null,
    username varchar(80) not null,
    display_name varchar(120) not null,
    password_hash varchar(255) not null,
    role varchar(30) not null,
    enabled boolean not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint pk_app_users primary key (id),
    constraint uk_app_users_username unique (username)
);

create index idx_app_users_username on app_users (username);