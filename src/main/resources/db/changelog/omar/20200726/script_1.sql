create table roles
(
  id      int(11) AUTO_INCREMENT PRIMARY KEY,
  name_kz varchar(20) not null,
  name_ru varchar(20) not null,
  name_en varchar(20) not null
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

create table users
(
  id                        int(11) AUTO_INCREMENT PRIMARY KEY,
  email                     varchar(120) not null,
  email_verification_status int(1)       not null default 0,
  email_verification_token  varchar(255),
  encrypted_password        varchar(255) not null,
  first_name                varchar(120) not null,
  last_name                 varchar(120) not null,
  patronymic                varchar(120),
  user_id                   varchar(255) not null
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;

CREATE TABLE user_roles
(
  user_id int(11) NOT NULL,
  role_id int(11) NOT NULL,
  PRIMARY KEY (user_id, role_id),
  CONSTRAINT fk_user_roles_users
    FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_user_roles_roles
    FOREIGN KEY (role_id) REFERENCES roles (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;