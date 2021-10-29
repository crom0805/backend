INSERT INTO USER (USERID, PASSWORD, NICKNAME, ACTIVATED) VALUES
('admin', '$2a$10$YUwg2k9pA5lcFgtFteK4T.AApk778u5iu6HF2pYbSaKzgqyfbzJLK', 'admin', 1);

INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_USER');
INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_ADMIN');

INSERT INTO USER_AUTHORITY (SEQ, AUTHORITY_NAME) values (1, 'ROLE_USER');
INSERT INTO USER_AUTHORITY (SEQ, AUTHORITY_NAME) values (1, 'ROLE_ADMIN');

/*

CREATE TABLE `authority` (
  `authority_name` varchar(50) NOT NULL,
  PRIMARY KEY (`authority_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `user` (
  `seq` bigint(20) NOT NULL AUTO_INCREMENT,
  `userid` varchar(50) DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  `nickname` varchar(50) DEFAULT NULL,
  `activated` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `user_authority` (
  `seq` bigint(20) NOT NULL,
  `authority_name` varchar(50) NOT NULL,
  PRIMARY KEY (`seq`,`authority_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

*/