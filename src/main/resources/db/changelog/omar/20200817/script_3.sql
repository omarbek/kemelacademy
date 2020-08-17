create table files
(
  id           int(11) AUTO_INCREMENT PRIMARY KEY,
  lesson_id    int(11),
  file_type_id int(11)      not null,
  name         varchar(255) not null,
  CONSTRAINT fk_files_lessons
    FOREIGN KEY (lesson_id) REFERENCES lessons (id),
  CONSTRAINT fk_files_file_types
    FOREIGN KEY (file_type_id) REFERENCES file_types (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;