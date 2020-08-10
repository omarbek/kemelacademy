create table password_reset_tokens
(
  id                       int(11) AUTO_INCREMENT PRIMARY KEY,
  user_id                  int(11) NOT NULL,
  email_verification_token varchar(255),
  CONSTRAINT fk_password_reset_tokens_users
    FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;