ALTER TABLE lessons
  DROP FOREIGN KEY fk_lessons_lesson_types;
alter table lessons
  drop column lesson_type_id;