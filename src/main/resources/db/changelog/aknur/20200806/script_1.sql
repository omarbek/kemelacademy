create table languages
(
  id      int(11) AUTO_INCREMENT PRIMARY KEY,
  name_kz varchar(255) not null,
  name_ru varchar(255) not null,
  name_en varchar(255) not null
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

insert into languages (name_kz, name_ru, name_en)
values ('Қазақ', 'Казахский', 'Kazakh');

insert into languages (name_kz, name_ru, name_en)
values ('Орыс', 'Русский', 'Russian');

insert into languages (name_kz, name_ru, name_en)
values ('Ағылшын', 'Английский', 'English');