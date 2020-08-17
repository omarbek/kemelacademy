create table video
(
  id          int(11) AUTO_INCREMENT PRIMARY KEY,
  lesson_id   int(11)      not null,
  url         varchar(255) not null,
  always_open int(1)       not null default 0,
  CONSTRAINT fk_video_lessons
    FOREIGN KEY (lesson_id) REFERENCES lessons (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;