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