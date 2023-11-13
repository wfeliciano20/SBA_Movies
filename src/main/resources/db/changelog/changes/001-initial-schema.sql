--liquibase formatted sql
--changeset wfeliciano20:001 splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS movies (
                                      id BIGSERIAL PRIMARY KEY,
                                      title VARCHAR(255) NOT NULL UNIQUE,
                                      description TEXT NOT NULL,
                                      release_year INTEGER NOT NULL,
                                      rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5),
                                      created TIMESTAMP,
                                      updated TIMESTAMP
);

-- rollback drop table if exists movies cascade;


CREATE TABLE IF NOT EXISTS directors (
                                         id BIGSERIAL PRIMARY KEY,
                                         name VARCHAR(255) NOT NULL
);

-- rollback drop table if exists directors;

CREATE TABLE IF NOT EXISTS actors (
                                      id BIGSERIAL PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL
);

-- rollback drop table if exists actors;

CREATE TABLE IF NOT EXISTS genres (
                                      id BIGSERIAL PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL
);

-- rollback drop table if exists genres;

CREATE TABLE IF NOT EXISTS movie_directors (
                                               movie_id BIGINT,
                                               director_id BIGINT,
                                               FOREIGN KEY (movie_id) REFERENCES movies (id),
                                               FOREIGN KEY (director_id) REFERENCES directors (id)
);

-- rollback drop table if exists movie_directors cascade;

CREATE TABLE IF NOT EXISTS movie_actors (
                                            movie_id BIGINT,
                                            actor_id BIGINT,
                                            FOREIGN KEY (movie_id) REFERENCES movies (id),
                                            FOREIGN KEY (actor_id) REFERENCES actors (id)
);

-- rollback drop table if exists movie_actors cascade;

CREATE TABLE IF NOT EXISTS movie_genres (
                                            movie_id BIGINT,
                                            genre_id BIGINT,
                                            FOREIGN KEY (movie_id) REFERENCES movies (id),
                                            FOREIGN KEY (genre_id) REFERENCES genres (id)
);

-- rollback drop table if exists movie_genres cascade;


