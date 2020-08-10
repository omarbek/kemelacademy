create table courses
(
  id             int(11) AUTO_INCREMENT PRIMARY KEY,
  author_id       int(11)      not null,
  category_id     int(11)      not null,
  level_id        int(11)      not null,
  language_id     int(11)      not null,
  price           int(10)      not null,
  name_kz         varchar(255) not null,
  name_ru         varchar(255) not null,
  name_en         varchar(255) not null,
  description_kz  text         not null,
  description_ru  text         not null,
  description_en  text         not null,
  about_course_kz text         not null,
  about_course_ru text         not null,
  about_course_en text         not null,
  CONSTRAINT fk_courses_users
    FOREIGN KEY (author_id) REFERENCES users (id),
  CONSTRAINT fk_courses_categories
    FOREIGN KEY (category_id) REFERENCES categories (id),
  CONSTRAINT fk_courses_levels
    FOREIGN KEY (level_id) REFERENCES levels (id),
  CONSTRAINT fk_courses_languages
    FOREIGN KEY (language_id) REFERENCES languages (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;