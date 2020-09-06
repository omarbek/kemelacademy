create table user_courses
(
  id        int(11) auto_increment primary key,
  user_id   int(11) not null,
  course_id int(11) not null,
  finished  int(1)  not null default 0,
  CONSTRAINT fk_user_courses_users
    FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_user_courses_courses
    FOREIGN KEY (course_id) REFERENCES courses (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;