INSERT INTO directors (name) VALUES
                                 ('Lana Wachowski'),
                                 ('Christopher Nolan'),
                                 ('Pete Docter');

-- Insert sample actors
INSERT INTO actors (name) VALUES
                              ('Keanu Reeves'),
                              ('Laurence Fishburne'),
                              ('Carrie-Anne Moss'),
                              ('Heath Ledger'),
                              ('Christian Bale'),
                              ('Ellen Page');

-- Insert sample genres
INSERT INTO genres (name) VALUES
                              ('Action'),
                              ('Sci-Fi'),
                              ('Crime'),
                              ('Animation'),
                              ('Adventure');

-- Insert sample movies
INSERT INTO movies (title, description, release_year, rating, created, updated) VALUES

                                                                                    ('The Matrix', 'A computer hacker learns about the true nature of his reality and his role in the war against its controllers.', 1999, 4, NOW(), NOW()),
                                                                                    ('The Dark Knight', 'When the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.', 2008, 5, NOW(), NOW()),
                                                                                    ('Up', 'Seventy-eight-year-old Carl sets out to fulfill his lifelong dream to see the wilds of South America.', 2009, 4, NOW(), NOW());

-- Link movies with directors, actors, and genres
-- You can customize these links according to your requirements
-- Here, each movie is linked with one director, a set of actors, and one or more genres
INSERT INTO movie_directors (movie_id, director_id) VALUES
                                                        (1, 1), -- The Matrix - Lana Wachowski
                                                        (2, 2), -- The Dark Knight - Christopher Nolan
                                                        (3, 3); -- Up - Pete Docter

INSERT INTO movie_actors (movie_id, actor_id) VALUES
                                                  (1, 1), -- The Matrix - Keanu Reeves
                                                  (1, 2), -- The Matrix - Laurence Fishburne
                                                  (1, 3), -- The Matrix - Carrie-Anne Moss
                                                  (2, 4), -- The Dark Knight - Heath Ledger
                                                  (2, 5), -- The Dark Knight - Christian Bale
                                                  (3, 6); -- Up - Ellen Page

INSERT INTO movie_genres (movie_id, genre_id) VALUES
                                                  (1, 1), -- The Matrix - Action
                                                  (1, 2), -- The Matrix - Sci-Fi
                                                  (2, 1), -- The Dark Knight - Action
                                                  (2, 3), -- The Dark Knight - Crime
                                                  (3, 4); -- Up - Animation