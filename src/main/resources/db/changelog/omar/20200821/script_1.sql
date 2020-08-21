create table user_tests
(
  id        int(11) auto_increment primary key,
  user_id   int(11) not null,
  test_id   int(11) not null,
  status_id int(11) not null,
  grade     int(3),
  comment   varchar(255),
  CONSTRAINT fk_user_tests_users
    FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_user_tests_tests
    FOREIGN KEY (test_id) REFERENCES tests (id),
  CONSTRAINT fk_user_tests_test_statuses
    FOREIGN KEY (status_id) REFERENCES test_statuses (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

create table test_files
(
  user_test_id int(11) not null,
  file_id      int(11) not null,
  CONSTRAINT fk_test_files_user_tests
    FOREIGN KEY (user_test_id) REFERENCES user_tests (id),
  CONSTRAINT fk_test_files_files
    FOREIGN KEY (file_id) REFERENCES files (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;