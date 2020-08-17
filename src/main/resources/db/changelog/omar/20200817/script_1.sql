create table lessons
(
  id             int(11) AUTO_INCREMENT PRIMARY KEY,
  lesson_type_id int(11)      not null,
  chapter_id     int(11)      not null,
  lesson_no      int(3)       not null,
  name_kz        varchar(255) not null,
  name_ru        varchar(255) not null,
  name_en        varchar(255) not null,
  duration       int(4)       not null default 1,
  deleted        int(1)       not null default 0,
  CONSTRAINT fk_lessons_lesson_types
    FOREIGN KEY (lesson_type_id) REFERENCES lesson_types (id),
  CONSTRAINT fk_lessons_chapters
    FOREIGN KEY (chapter_id) REFERENCES chapters (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;