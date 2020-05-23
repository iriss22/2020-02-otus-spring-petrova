# Библиотека:

Есть таблицы с книгами, авторами и жанрами, комментариями к книгам
Реализованы CRUD для таблицы книг.

Обобенности реализации:
* Spring Boot приложение
* Использую Spring Data MongoDB
* Использование БД MongoDB
* Интерфейс на Spring Shell
* В тестах используется MongoDB

# Тестирование
Для тестирования можно использовать докер образ с MongoDB (https://hub.docker.com/_/mongo)

Запуск: docker run -d -p 27017-27019:27017-27019 --name mongodb mongo:3.6.17
