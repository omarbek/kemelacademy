alter table courses
  add column certificate_id int(11);
ALTER TABLE courses
  ADD FOREIGN KEY (certificate_id) REFERENCES files (id);