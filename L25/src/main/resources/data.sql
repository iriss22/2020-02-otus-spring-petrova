INSERT INTO author (name) VALUES('Фримен');
INSERT INTO genre (name) VALUES('Информационные технологии');
INSERT INTO book (genre_id, author_id, name) VALUES(1, 1, 'Паттерны проектирования');
INSERT INTO comment (book_id, text) VALUES(1, 'Очень классная книга по паттернам!');
INSERT INTO comment (book_id, text) VALUES(1, 'Книга супер!!!');

INSERT INTO author (name) VALUES('Test');
INSERT INTO genre (name) VALUES('Test');
INSERT INTO book (genre_id, author_id, name) VALUES(2, 2, 'Test');
INSERT INTO comment (book_id, text) VALUES(2, 'Test!');
