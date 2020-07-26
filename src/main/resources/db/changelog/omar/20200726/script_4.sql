alter table roles
  add column (name varchar(40));

update roles
set name='ROLE_MODERATOR'
where id = 1;
update roles
set name='ROLE_INSTRUCTOR'
where id = 2;
update roles
set name='ROLE_STUDENT'
where id = 3;

ALTER TABLE roles
  CHANGE
    name
    name varchar(40) NOT NULL;