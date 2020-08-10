create table levels
(
  id      int(11) AUTO_INCREMENT PRIMARY KEY,
  name_kz varchar(255) not null,
  name_ru varchar(255) not null,
  name_en varchar(255) not null
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

insert into levels (id, name_kz, name_ru, name_en)
values (1, 'Бастауыш', 'Начальный', 'Beginner');
insert into levels (id, name_kz, name_ru, name_en)
values (2, 'Орташа', 'Средний', 'Intermediate');
insert into levels (id, name_kz, name_ru, name_en)
values (3, 'Жоғары', 'Высокий', 'Advanced');