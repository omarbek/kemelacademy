alter table user_home_works
  add column file_id int(11);
ALTER TABLE user_home_works
  ADD FOREIGN KEY (file_id) REFERENCES files (id);