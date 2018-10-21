DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', (select id from users where email='user@yandex.ru')),
  ('ROLE_ADMIN', (select id from users where email='admin@gmail.com'));

INSERT INTO meals (userid, datetime, description, calories) VALUES
  ((select id from users where email='user@yandex.ru'), '2018-10-20 09:00:00', 'завтрак', 500),
  ((select id from users where email='user@yandex.ru'), '2018-10-20 14:00:00', 'обед', 1000),
  ((select id from users where email='user@yandex.ru'), '2018-10-21 09:00:00', 'перекус', 150),
  ((select id from users where email='admin@gmail.com'), '2018-10-21 10:00:00', 'вкусный завтрак', 500)
;

