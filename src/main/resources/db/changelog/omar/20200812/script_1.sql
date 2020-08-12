create table chapters
(
  id         int(11) AUTO_INCREMENT PRIMARY KEY,
  course_id  int(11)      not null,
  chapter_no int(3)       not null,
  name_kz    varchar(255) not null,
  name_ru    varchar(255) not null,
  name_en    varchar(255) not null,
  CONSTRAINT fk_chapters_courses
    FOREIGN KEY (course_id) REFERENCES courses (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;