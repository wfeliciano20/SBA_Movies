-- Insert sample directors if they don't already exist
INSERT INTO directors (name)
SELECT name
FROM (VALUES
          ('Lana Wachowski'),
          ('Christopher Nolan'),
          ('Pete Docter')
     ) AS new_directors(name)
WHERE name NOT IN (SELECT name FROM directors);

-- Insert sample actors if they don't already exist
INSERT INTO actors (name)
SELECT name
FROM (VALUES
          ('Keanu Reeves'),
          ('Laurence Fishburne'),
          ('Carrie-Anne Moss'),
          ('Heath Ledger'),
          ('Christian Bale'),
          ('Ellen Page')
     ) AS new_actors(name)
WHERE name NOT IN (SELECT name FROM actors);

-- Insert sample genres if they don't already exist
INSERT INTO genres (name)
SELECT name
FROM (VALUES
          ('Action'),
          ('Sci-Fi'),
          ('Crime'),
          ('Animation'),
          ('Adventure')
     ) AS new_genres(name)
WHERE name NOT IN (SELECT name FROM genres);

-- Insert sample movies if they don't already exist
INSERT INTO movies (title, description, release_year, rating, created, updated)
SELECT title, description, release_year, rating, NOW(), NOW()
FROM (VALUES
          ('The Matrix', 'A computer hacker learns about the true nature of his reality and his role in the war against its controllers.', 1999, 4),
          ('The Dark Knight', 'When the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.', 2008, 5),
          ('Up', 'Seventy-eight-year-old Carl sets out to fulfill his lifelong dream to see the wilds of South America.', 2009, 4)
     ) AS new_movies(title, description, release_year, rating)
WHERE title NOT IN (SELECT title FROM movies);



-- Link movies with directors, but insert only if the relationship doesn't already exist
INSERT INTO movie_directors (movie_id, director_id)
SELECT movie_id, director_id
FROM (VALUES
          (1, 1), -- The Matrix - Lana Wachowski
          (2, 2), -- The Dark Knight - Christopher Nolan
          (3, 3)  -- Up - Pete Docter
     ) AS new_movie_directors(movie_id, director_id)
WHERE (movie_id, director_id) NOT IN (SELECT movie_id, director_id FROM movie_directors);

-- Link movies with actors, but insert only if the relationship doesn't already exist
INSERT INTO movie_actors (movie_id, actor_id)
SELECT movie_id, actor_id
FROM (VALUES
          (1, 1), -- The Matrix - Keanu Reeves
          (1, 2), -- The Matrix - Laurence Fishburne
          (1, 3), -- The Matrix - Carrie-Anne Moss
          (2, 4), -- The Dark Knight - Heath Ledger
          (2, 5), -- The Dark Knight - Christian Bale
          (3, 6)  -- Up - Ellen Page
     ) AS new_movie_actors(movie_id, actor_id)
WHERE (movie_id, actor_id) NOT IN (SELECT movie_id, actor_id FROM movie_actors);

-- Link movies with genres, but insert only if the relationship doesn't already exist
INSERT INTO movie_genres (movie_id, genre_id)
SELECT movie_id, genre_id
FROM (VALUES
          (1, 1), -- The Matrix - Action
          (1, 2), -- The Matrix - Sci-Fi
          (2, 1), -- The Dark Knight - Action
          (2, 3), -- The Dark Knight - Crime
          (3, 4)  -- Up - Animation
     ) AS new_movie_genres(movie_id, genre_id)
WHERE (movie_id, genre_id) NOT IN (SELECT movie_id, genre_id FROM movie_genres);
