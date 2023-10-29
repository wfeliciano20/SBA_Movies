package com.williamfeliciano.sba_movies.repositories;

import com.williamfeliciano.sba_movies.entities.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void given_when_then() {
        List<Movie> movies = movieRepository.findAll();

        assertThat(movies).isNotNull();
        assertThat(movies.size()).isEqualTo(3);
        assertThat(movies.get(0).getTitle()).isEqualTo("The Matrix");
        assertThat(movies.get(1).getTitle()).isEqualTo("The Dark Knight");
        assertThat(movies.get(2).getTitle()).isEqualTo("Up");
    }
}
