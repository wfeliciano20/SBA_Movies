CREATE TABLE IF NOT EXISTS movies (
                                      id BIGSERIAL PRIMARY KEY,
                                      title VARCHAR(255) NOT NULL UNIQUE,
    description TEXT NOT NULL,
    release_year INTEGER NOT NULL,
    rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5),
    created TIMESTAMP,
    updated TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS directors (
                                         id BIGSERIAL PRIMARY KEY,
                                         name VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS actors (
                                      id BIGSERIAL PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS genres (
                                      id BIGSERIAL PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS movie_directors (
                                               movie_id BIGINT,
                                               director_id BIGINT,
                                               FOREIGN KEY (movie_id) REFERENCES movies (id),
    FOREIGN KEY (director_id) REFERENCES directors (id)
    );

CREATE TABLE IF NOT EXISTS movie_actors (
                                            movie_id BIGINT,
                                            actor_id BIGINT,
                                            FOREIGN KEY (movie_id) REFERENCES movies (id),
    FOREIGN KEY (actor_id) REFERENCES actors (id)
    );

CREATE TABLE IF NOT EXISTS movie_genres (
                                            movie_id BIGINT,
                                            genre_id BIGINT,
                                            FOREIGN KEY (movie_id) REFERENCES movies (id),
    FOREIGN KEY (genre_id) REFERENCES genres (id)
    );


-- to test the h2 use this