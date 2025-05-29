-- liquibase formatted sql

-- changeset farabi:1
CREATE TABLE user (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(255) NOT NULL,
                      email VARCHAR(255) NOT NULL,
                      password VARCHAR(255) NOT NULL,
                      role VARCHAR(20) NOT NULL,
                      CONSTRAINT uk_user_username UNIQUE (username),
                      CONSTRAINT uk_user_email UNIQUE (email)
);

-- changeset farabi:2
CREATE TABLE task (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      description TEXT,
                      status VARCHAR(20) NOT NULL,
                      created_at DATETIME NOT NULL,
                      updated_at DATETIME,
                      user_id INT NOT NULL,
                      CONSTRAINT fk_task_user FOREIGN KEY (user_id) REFERENCES user(id)
);

-- changeset farabi:3
INSERT INTO user (email, password, role, username)
VALUES(
          'admin@mail.ru',
          '$2a$12$4qG5hzzkmjw2GLdbJgOfQ.JKv7LmzEVhKT/KLj6CXR3K1M6by2xLu',
          'ADMIN',
          'admin'
      );