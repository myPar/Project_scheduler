create table users (
    id BIGSERIAL primary key,
    first_name text NOT NULL,
    last_name text,
    user_password text NOT NULL
);

create table tags (
    id BIGSERIAL primary key,
    tag_name text NOT NULL
);

create table tasks (
    id BIGSERIAL primary key,
    task_name text not null,
    description text default '',
    user_id BIGINT REFERENCES users (id) NOT NULL,

    duration BIGINT,
    start_time BIGINT,
    difficult INTEGER,
    completed boolean default false
);

/* table for storing task's tags list, so task_id and tag_id are not null */
create table tasks_tags (
    id BIGSERIAL primary key,
    task_id BIGINT references tasks(id) ON DELETE CASCADE NOT NULL,
    tag_id BIGINT references tags(id) ON DELETE CASCADE NOT NULL
);
