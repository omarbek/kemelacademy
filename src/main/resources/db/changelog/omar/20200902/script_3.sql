alter table courses
  drop column name_kz;
alter table courses
  drop column name_ru;
alter table courses
  drop column name_en;
alter table courses
  drop column description_kz;
alter table courses
  drop column description_ru;
alter table courses
  drop column description_en;
alter table courses
  drop column about_course_kz;
alter table courses
  drop column about_course_ru;
alter table courses
  drop column about_course_en;
alter table courses
  add column name varchar(255) not null;
alter table courses
  add column description varchar(255) not null;
alter table courses
  add column about_course varchar(255) not null;