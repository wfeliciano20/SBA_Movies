package com.williamfeliciano.sba_movies.repositories;

import com.williamfeliciano.sba_movies.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
