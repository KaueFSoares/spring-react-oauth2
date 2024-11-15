INSERT INTO users (username, password)
VALUES ('${ADMIN_USERNAME}', '${ADMIN_PASSWORD}'),
       ('${REGULAR_USER_USERNAME}', '${REGULAR_USER_PASSWORD}');

INSERT INTO users_roles (user_id, role)
SELECT u.id, 'ADMIN'
FROM users u
WHERE u.username = '${ADMIN_USERNAME}';

INSERT INTO users_roles (user_id, role)
SELECT u.id, 'USER'
FROM users u
WHERE u.username = '${REGULAR_USER_USERNAME}';