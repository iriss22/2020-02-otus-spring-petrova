DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS genre;

CREATE TABLE author (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE genre (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE book (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    author_id BIGINT NOT NULL REFERENCES author(id),
    genre_id BIGINT NOT NULL REFERENCES genre(id),
    UNIQUE(name, author_id, genre_id)
);

CREATE TABLE comment (
    id BIGSERIAL PRIMARY KEY,
    text VARCHAR(500) NOT NULL,
    book_id BIGINT NOT NULL REFERENCES book(id)
);

CREATE TABLE user (
  id BIGSERIAL PRIMARY KEY,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  enabled BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (id)
);

CREATE TABLE authority (
  id BIGSERIAL PRIMARY KEY,
  username VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES user(username)
);
CREATE UNIQUE index ix_auth_username ON authority (username, authority);
