create table course_statuses
(
  id      int(11) AUTO_INCREMENT PRIMARY KEY,
  name_kz varchar(255) not null,
  name_ru varchar(255) not null,
  name_en varchar(255) not null
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

insert into course_statuses (id, name_kz, name_ru, name_en)
values (1, 'Акылы', 'Платный', 'Paid');
insert into course_statuses (id, name_kz, name_ru, name_en)
values (2, 'Тегин', 'Беслатный', 'Free');
insert into course_statuses (id, name_kz, name_ru, name_en)
values (3, 'Жабык', 'Для определенных людей', 'Private');

alter table courses
  add column course_status_id int(11) not null default 1;
ALTER TABLE courses
  ADD FOREIGN KEY (course_status_id) REFERENCES course_statuses (id);