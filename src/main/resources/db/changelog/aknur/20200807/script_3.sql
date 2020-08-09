create table test_statuses
(
  id      int(11) AUTO_INCREMENT PRIMARY KEY,
  name_kz varchar(255) not null,
  name_ru varchar(255) not null,
  name_en varchar(255) not null
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

insert into test_statuses (name_kz, name_ru, name_en)
values ('ашылмаған', 'не открытый', 'not open');

insert into test_statuses (name_kz, name_ru, name_en)
values ('жүктелген', 'загруженное', 'downloaded');

insert into test_statuses (name_kz, name_ru, name_en)
values ('аяқталған', 'законченный', 'complete');