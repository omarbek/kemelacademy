ALTER TABLE home_work_files
  CHANGE user_test_id user_home_work_id int(11);
ALTER TABLE user_home_works
  CHANGE test_id home_work_id int(11);