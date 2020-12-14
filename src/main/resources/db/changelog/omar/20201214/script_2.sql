alter table video
  add column created_date TIMESTAMP not null default CURRENT_TIMESTAMP;