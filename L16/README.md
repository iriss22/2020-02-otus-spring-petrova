# Библиотека:

Есть таблицы с книгами, авторами и жанрами, комментариями к книгам
Реализованы CRUD для таблицы книг.

Обобенности реализации:
* Spring Boot приложение
* Использую Spring Data JPA
* Использование БД PostgreSQL
* Spring MVC
* В тестах используется H2

# Тестирование
Для тестирования можно использовать докер образ с PostgreSQL (https://hub.docker.com/_/postgres)

Запуск:                docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword postgres

## Выполнить запрос руками
Войти в контейнер:     docker exec -it <id> bash
Подключиться к psql:   psql -U postgres
