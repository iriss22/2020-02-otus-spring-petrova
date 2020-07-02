drop table if exists negotiation;
drop table if exists resume;
drop table if exists vacancy;

create table resume (
    id BIGSERIAL PRIMARY KEY,
    first_name varchar(50),
    last_name varchar(50),
    description varchar(5000)
);

create table vacancy (
    id BIGSERIAL PRIMARY KEY,
    description varchar(5000)
);

create table negotiation (
    id BIGSERIAL PRIMARY KEY,
    author_id BIGINT NOT NULL REFERENCES resume(id),
    vacancy_id BIGINT NOT NULL REFERENCES vacancy(id),
    author varchar(50)
);
