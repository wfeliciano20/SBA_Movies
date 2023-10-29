package com.williamfeliciano.sba_movies.repositories;

import com.williamfeliciano.sba_movies.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Long> {
}
