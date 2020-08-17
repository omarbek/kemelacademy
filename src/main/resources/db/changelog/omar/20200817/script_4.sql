create table tests
(
  id          int(11) AUTO_INCREMENT PRIMARY KEY,
  lesson_id   int(11) not null,
  file_id     int(11),#for download
  description text,
  CONSTRAINT fk_tests_lessons
    FOREIGN KEY (lesson_id) REFERENCES lessons (id),
  CONSTRAINT fk_tests_files
    FOREIGN KEY (file_id) REFERENCES files (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;