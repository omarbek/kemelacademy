alter table lessons drop column name_kz;
alter table lessons drop column name_ru;
alter table lessons drop column name_en;
alter table lessons add column name varchar(255) not null;