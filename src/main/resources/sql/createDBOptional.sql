create database optional_database;
use optional_database;

CREATE TABLE user
(
    id       INTEGER AUTO_INCREMENT,
    role     ENUM ('STUDENT','TEACHER'),
    name     VARCHAR(25) not null,
    surname  VARCHAR(25) not null,
    login    VARCHAR(40) NOT NULL unique,
    password VARCHAR(25) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE course
(
    id         INTEGER AUTO_INCREMENT,
    title      VARCHAR(25),
    teacher_id int,
    description      VARCHAR(225),
    FOREIGN KEY (teacher_id) references user (id),
    PRIMARY KEY (id)
);

CREATE TABLE comment
(
    id         INTEGER AUTO_INCREMENT,
    message    VARCHAR(250),
    teacher_id INT,
    FOREIGN KEY (teacher_id) REFERENCES user (id),
    student_id INT,
    FOREIGN KEY (student_id) REFERENCES user (id),
    date       TIMESTAMP default CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE student_course
(
    user_id   INT,
    FOREIGN KEY (user_id) REFERENCES user (id),
    course_id INT,
    FOREIGN KEY (course_id) REFERENCES course (id),
    PRIMARY KEY (user_id, course_id)
);