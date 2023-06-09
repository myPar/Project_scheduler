insert into users (first_name, last_name, user_password) values ('Andrey', 'Bobrovskii', 'password');
INSERT INTO tasks (task_name, description, user_id, duration, difficult) VALUES ('first task', 'first task for Andrey', 1, 100, 2);
INSERT INTO tasks (task_name, description, user_id, duration, difficult) VALUES ('second task', 'second task for Andrey', 1, 100, 1);
INSERT INTO tasks (task_name, description, user_id, duration, difficult) VALUES ('third task', 'first task for Andrey', 1, 100, 0);
INSERT INTO tasks (task_name, description, user_id, duration, difficult) VALUES ('create project', 'custom description', 1, 100, 3);
INSERT INTO tasks (task_name, description, user_id, duration, difficult) VALUES ('read the book', 'read 10 pages of technical book', 1, 100, 0);
INSERT INTO tasks (task_name, description, user_id, duration, difficult) VALUES ('write the essay', 'finish the essay', 1, 100, 2);

INSERT INTO tags(tag_name) VALUES ('important');
INSERT INTO tags(tag_name) VALUES ('self-education');
INSERT INTO tags(tag_name) VALUES ('university');

INSERT INTO tasks_tags(task_id, tag_id) VALUES (1, 1);
INSERT INTO tasks_tags(task_id, tag_id) VALUES (1, 2);
INSERT INTO tasks_tags(task_id, tag_id) VALUES (2, 3);
INSERT INTO tasks_tags(task_id, tag_id) VALUES (3, 3);
INSERT INTO tasks_tags(task_id, tag_id) VALUES (4, 1);
INSERT INTO tasks_tags(task_id, tag_id) VALUES (4, 3);
INSERT INTO tasks_tags(task_id, tag_id) VALUES (5, 2);
INSERT INTO tasks_tags(task_id, tag_id) VALUES (6, 2);

INSERT INTO schedules(user_id, start_time, end_time, schedule_name) VALUES (1, 100000, 120000, 'finish project');

INSERT INTO schedule_items(schedule_id, difficult, count_to_complete) VALUES (1, 2, 2);
INSERT INTO schedule_items(schedule_id, difficult, count_to_complete) VALUES (1, 1, 3);
INSERT INTO schedule_items(schedule_id, difficult, count_to_complete) VALUES (1, 3, 1);

INSERT INTO notes(user_id, note_name, note_text, creation_time) VALUES (1, 'first note', 'this is my first note and I have no idea what to write here..', 100000);
INSERT INTO notes_tags(note_id, tag_id) VALUES (1, 1);
INSERT INTO notes_tags(note_id, tag_id) VALUES (1, 2);
INSERT INTO notes_tags(note_id, tag_id) VALUES (1, 3);
