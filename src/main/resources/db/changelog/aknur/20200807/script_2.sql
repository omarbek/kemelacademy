create table file_types
(
  id      int(11) AUTO_INCREMENT PRIMARY KEY,
  name_kz varchar(255) not null,
  name_ru varchar(255) not null,
  name_en varchar(255) not null
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

insert into file_types (name_kz, name_ru, name_en)
values ('жүктеу үшін', 'для скачивания', 'for download');

insert into file_types (name_kz, name_ru, name_en)
values ('үй тапсырмасы үшін(тесттер)', 'для домашнего задания(тестов)', 'for homework(tests)');

insert into file_types (name_kz, name_ru, name_en)
values ('сертификат үшін', 'для сертификата', 'for certificate');