CREATE TABLE copsboot_user
(
    id             uuid NOT NULL PRIMARY KEY,
    auth_server_id uuid,
    email          VARCHAR(255),
    mobile_token   VARCHAR(255)
);
