alter table courses
  add column deleted int(1) not null default 0;