create table categories
(
  id      int(11) AUTO_INCREMENT PRIMARY KEY,
  name_kz varchar(255) not null,
  name_ru varchar(255) not null,
  name_en varchar(255) not null
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;