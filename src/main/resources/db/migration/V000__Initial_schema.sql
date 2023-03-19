CREATE TABLE users (
	id BIGSERIAL NOT NULL,
    username VARCHAR(255) NOT NULL,
    saltedPasswordHash VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(255) NOT NULL,
	accountState VARCHAR(20) NOT NULL,
	invalidLoginAttempts SMALLINT NOT NULL DEFAULT 0,
	created TIMESTAMP NOT NULL,
	lastLogin TIMESTAMP NULL DEFAULT NULL,
	PRIMARY KEY(id),
	UNIQUE(username)
);

CREATE TABLE organization (
	id BIGSERIAL NOT NULL,
	name VARCHAR(255) NOT NULL,
	PRIMARY KEY(id),
	UNIQUE(name)
);

CREATE TABLE userpermission (
	id BIGSERIAL NOT NULL,
	permission VARCHAR(25) NOT NULL,
	userId BIGINT NOT NULL,
	organizationId BIGINT NULL,
	type VARCHAR(20) NOT NULL,
	created TIMESTAMP NOT NULL,
	PRIMARY KEY(id),
	UNIQUE(userId,permission,organizationId),
	FOREIGN KEY (userId) REFERENCES users(id),
	FOREIGN KEY (organizationId) REFERENCES organization(id)
);

CREATE TABLE token (
	id BIGSERIAL NOT NULL,
	userId BIGINT NOT NULL,
	token VARCHAR(255) NOT NULL,
	state VARCHAR(20) NOT NULL,
	type VARCHAR(20) NOT NULL,
	created TIMESTAMP NOT NULL,
	expiresAt TIMESTAMP NULL,
	PRIMARY KEY(id),
	UNIQUE(token),
	FOREIGN KEY (userId) REFERENCES users(id)
);

CREATE TABLE tokenstatechange (
	id BIGSERIAL NOT NULL,
	tokenId BIGINT NOT NULL,
	previousState VARCHAR(20) NOT NULL,
	newState VARCHAR(20) NOT NULL,
	created TIMESTAMP NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (tokenId) REFERENCES token(id)
);