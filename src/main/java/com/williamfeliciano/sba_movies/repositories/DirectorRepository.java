package com.williamfeliciano.sba_movies.repositories;

import com.williamfeliciano.sba_movies.entities.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director, Long> {
}
