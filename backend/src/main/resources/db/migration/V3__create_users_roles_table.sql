CREATE TABLE IF NOT EXISTS users_roles
(
    user_id INT NOT NULL,
    role role_type NOT NULL,
    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);