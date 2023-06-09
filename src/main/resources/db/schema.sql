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
    user_id BIGINT REFERENCES users (id) ON DELETE CASCADE NOT NULL,

    duration BIGINT,
    start_time BIGINT,
    difficult INTEGER,
    completed boolean default false NOT NULL,
    overdue BOOLEAN default false NOT NULL
);

/* table for storing task's tags list, so task_id and tag_id are not null */
create table tasks_tags (
    id BIGSERIAL primary key,
    task_id BIGINT references tasks(id) ON DELETE CASCADE NOT NULL,
    tag_id BIGINT references tags(id) ON DELETE CASCADE NOT NULL
);

create table schedules (
    id BIGSERIAL primary key,
    user_id BIGINT REFERENCES users (id) ON DELETE CASCADE NOT NULL,
    start_time BIGINT,
    end_time BIGINT,
    schedule_name TEXT NOT NULL,
    completed BOOLEAN default false NOT NULL,
    overdue BOOLEAN default false NOT NULL,

    CONSTRAINT time_interval_constraint CHECK(start_time < end_time)
);

create table schedule_items (
    id BIGSERIAL primary key,
    schedule_id BIGINT REFERENCES schedules(id) ON DELETE CASCADE NOT NULL,
    difficult INTEGER NOT NULL,
    count_to_complete INTEGER CHECK(count_to_complete > 0) NOT NULL
);

create table completed_task_data (
  id BIGSERIAL primary key,
  user_id BIGINT REFERENCES users(id) ON DELETE CASCADE NOT NULL,
  difficult INTEGER,
  duration BIGINT,
  completion_time BIGINT NOT NULL,
  schedule_completion BOOLEAN default false NOT NULL
);

create table notes (
    id BIGSERIAL primary key,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE NOT NULL,
    note_name TEXT NOT NULL,
    note_text TEXT NOT NULL,
    creation_time BIGINT NOT NULL
);

create table notes_tags (
    id BIGSERIAL primary key,
    note_id BIGINT REFERENCES notes(id) ON DELETE CASCADE NOT NULL,
    tag_id BIGINT REFERENCES tags(id) ON DELETE CASCADE NOT NULL
);