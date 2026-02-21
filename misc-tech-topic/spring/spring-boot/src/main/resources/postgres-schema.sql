CREATE SEQUENCE IF NOT EXISTS videos_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS users_seq  START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS roles_seq  START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS auth_seq   START WITH 1 INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS roles (
    id   BIGINT PRIMARY KEY DEFAULT nextval('roles_seq'::regclass),
    name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS authorities (
    id    BIGINT PRIMARY KEY DEFAULT nextval('auth_seq'::regclass),
    name  VARCHAR(32) NOT NULL UNIQUE,
    "grant" VARCHAR(128) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users (
    id       BIGINT PRIMARY KEY DEFAULT nextval('users_seq'::regclass),
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(128),
    role_id  BIGINT NOT NULL,
    CONSTRAINT fk_users_role
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS videos (
    id          BIGINT PRIMARY KEY DEFAULT nextval('videos_seq'::regclass),
    name        VARCHAR(128) NOT NULL UNIQUE,
    description VARCHAR(1024)
);

CREATE TABLE IF NOT EXISTS role_authority (
    role_id      BIGINT NOT NULL,
    authority_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, authority_id),
    CONSTRAINT fk_role_authority_role
    FOREIGN KEY (role_id) REFERENCES roles(id),
    CONSTRAINT fk_role_authority_authority
    FOREIGN KEY (authority_id) REFERENCES authorities(id)
);

ALTER SEQUENCE roles_seq  OWNED BY roles.id;
ALTER SEQUENCE auth_seq   OWNED BY authorities.id;
ALTER SEQUENCE users_seq  OWNED BY users.id;
ALTER SEQUENCE videos_seq OWNED BY videos.id;
