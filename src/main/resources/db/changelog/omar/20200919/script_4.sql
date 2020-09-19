alter table users
  drop column first_name;
alter table users
  drop column last_name;
alter table users
  drop column patronymic;
alter table users
  add column full_name varchar(255) not null default '';