INSERT INTO users (id, email, email_verification_status, email_verification_token, encrypted_password,
                                   first_name, last_name, patronymic, user_id)
VALUES (1, 'admin@kemelacademy.kz', 1,
        'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJUNnB0SmhWMU9UdkJyZTVhS1hwZUlvU3BrZnVBN3oiLCJleHAiOjE1OTY4MTMzMzl9.P6tAQMY_Jz7PHcltXt9nUt2c8lRVoAtb0w2NQLl9PVSJ6qQFNlfdOj5Hxf3iHlVXXxovjiFt7sl6Y5tVpA3rmw',
        '$2a$10$B6fqKNjTmZIL7fU97Ogjw.jbvabl.EVxxSB/icMFC.iJ7ck/r6a/K', 'Admin', 'Админов', '',
        'T6ptJhV1OTvBre5aKXpeIoSpkfuA7z');

insert into user_roles (user_id, role_id)
values (1, 1);
insert into user_roles (user_id, role_id)
values (1, 2);
insert into user_roles (user_id, role_id)
values (1, 3);