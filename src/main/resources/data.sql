INSERT INTO USER (USERNAME, PASSWORD, NICKNAME, ACTIVATED) VALUES
('admin', '$2a$10$YUwg2k9pA5lcFgtFteK4T.AApk778u5iu6HF2pYbSaKzgqyfbzJLK', 'admin', 1);

INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_USER');
INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_ADMIN');

INSERT INTO USER_AUTHORITY (SEQ, AUTHORITY_NAME) values (1, 'ROLE_USER');
INSERT INTO USER_AUTHORITY (SEQ, AUTHORITY_NAME) values (1, 'ROLE_ADMIN');