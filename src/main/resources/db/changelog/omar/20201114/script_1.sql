alter table courses
  drop column accepted;

create table progress_statuses
(
  id      int(11) AUTO_INCREMENT PRIMARY KEY,
  name_kz varchar(255) not null,
  name_ru varchar(255) not null,
  name_en varchar(255) not null
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

insert into progress_statuses (id, name_kz, name_ru, name_en)
values (1, 'Курстің алғашқы түрі', 'Черновик', 'Draft');
insert into progress_statuses (id, name_kz, name_ru, name_en)
values (2, 'Курс қаралуда', 'В прогрессе', 'Reviewing');
insert into progress_statuses (id, name_kz, name_ru, name_en)
values (3, 'Құпталды', 'Подтвержден', 'Published');
insert into progress_statuses (id, name_kz, name_ru, name_en)
values (4, 'Қабылданбады', 'Отклонен', 'Declined');

alter table courses
  add column progress_status_id int(11) not null default 1;
ALTER TABLE courses
  ADD FOREIGN KEY (progress_status_id) REFERENCES progress_statuses (id);