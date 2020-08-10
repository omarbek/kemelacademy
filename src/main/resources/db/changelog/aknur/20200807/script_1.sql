create table lesson_types
(
  id      int(11) AUTO_INCREMENT PRIMARY KEY,
  name_kz varchar(255) not null,
  name_ru varchar(255) not null,
  name_en varchar(255) not null
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

insert into lesson_types (name_kz, name_ru, name_en)
values ('видео', 'видео', 'video');

insert into lesson_types (name_kz, name_ru, name_en)
values ('файл', 'файл', 'file');

insert into lesson_types (name_kz, name_ru, name_en)
values ('тест', 'тест', 'test');