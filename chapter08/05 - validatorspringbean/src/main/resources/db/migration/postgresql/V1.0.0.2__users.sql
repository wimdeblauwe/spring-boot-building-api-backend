CREATE TABLE copsboot_user (
  id       UUID NOT NULL,
  email    VARCHAR(255),
  password VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE user_roles (
  user_id UUID NOT NULL,
  roles   VARCHAR(255)
);

ALTER TABLE user_roles
  ADD CONSTRAINT FK7je59ku3x462eqxu4ss3das1s
FOREIGN KEY (user_id)
REFERENCES copsboot_user;
