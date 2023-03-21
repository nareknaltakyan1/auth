create table users_logintypes (
    id BIGSERIAL NOT NULL,
   	userId BIGINT NOT NULL,
    loginType varchar(255) NOT NULL,
    priority SMALLINT NOT NULL,
    value varchar(255),
    setValueDate TIMESTAMP NULL DEFAULT NULL,
    lastlogin TIMESTAMP NULL DEFAULT NULL,
    FOREIGN KEY (userId) REFERENCES users(id),
	PRIMARY KEY(id),
	UNIQUE(userId, loginType, priority)
);
