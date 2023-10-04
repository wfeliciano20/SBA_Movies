package com.williamfeliciano.sba_movies.repositories;

import com.williamfeliciano.sba_movies.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByTitle(String title);
}
