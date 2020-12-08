create table user_lessons
(
  id        int(11) AUTO_INCREMENT PRIMARY KEY,
  user_id   int(11) NOT NULL,
  lesson_id int(11) NOT NULL,
  finished  int(1)  not null default 0,
  CONSTRAINT fk_user_lessons_users
    FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_user_lessons_lessons
    FOREIGN KEY (lesson_id) REFERENCES lessons (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

ALTER TABLE user_lessons
  ADD UNIQUE unique_index (user_id, lesson_id);