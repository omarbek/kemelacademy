drop table user_courses;
create table user_courses
(
  user_id   int(11) not null,
  course_id int(11) not null,
  PRIMARY KEY (user_id, course_id),
  finished  int(1)  not null default 0,
  CONSTRAINT fk_user_courses_users
    FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_user_courses_courses
    FOREIGN KEY (course_id) REFERENCES courses (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;