CREATE TABLE course_users
(
  course_id int(11) NOT NULL,
  user_id int(11) NOT NULL,
  PRIMARY KEY (course_id, user_id),
  CONSTRAINT fk_course_users_courses
    FOREIGN KEY (course_id) REFERENCES courses (id),
  CONSTRAINT fk_course_users_users
    FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;