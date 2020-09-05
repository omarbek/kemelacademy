create table system_parameters
(
  id    int(11) AUTO_INCREMENT PRIMARY KEY,
  code  varchar(255) not null,
  value varchar(255) not null
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

insert into system_parameters (code, value)
values ('UPLOADED_FOLDER_LOCAL', '/Users/omar/Desktop/');
insert into system_parameters (code, value)
values ('UPLOADED_FOLDER_REAL', 'opt/tomcat/');

create table feature_flag
(
  id        int(11) AUTO_INCREMENT PRIMARY KEY,
  code      varchar(255) not null,
  switch_on int(1)       not null default 0
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

insert into feature_flag (code, switch_on)
values ('GET_UPLOADED_FOLDER_REAL', 0);