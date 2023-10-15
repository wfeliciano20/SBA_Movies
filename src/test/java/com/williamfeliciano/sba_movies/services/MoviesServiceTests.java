package com.williamfeliciano.sba_movies.services;

import com.williamfeliciano.sba_movies.dtos.CreateMovieDto;
import com.williamfeliciano.sba_movies.dtos.MovieDetailsDto;
import com.williamfeliciano.sba_movies.dtos.MovieDto;
import com.williamfeliciano.sba_movies.exceptions.AppException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MoviesServiceTests {

    @InjectMocks
    private static MoviesService moviesService;
    private static List<MovieDto> allMoviesMock;

    private static MovieDetailsDto movie1;

    @BeforeAll
    public static void setUp() {
        movie1 = MovieDetailsDto.builder()
                .id(1L)
                .title("The Shawshank Redemption")
                .description("Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.")
                .year(1994)
                .rating(5)
                .directors(List.of("Frank Darabont"))
                .actors(List.of("Tim Robbins", "Morgan Freeman"))
                .genres(List.of("Drama"))
                .build();

        MovieDetailsDto movie2 = MovieDetailsDto.builder()
                .id(2L)
                .title("The Godfather")
                .description("An organized crime dynasty's aging patriarch transfers control of his clandestine empire to his reluctant son.")
                .year(1972)
                .rating(5)
                .directors(List.of("Francis Ford Coppola"))
                .actors(List.of("Marlon Brando", "Al Pacino"))
                .genres(List.of("Crime", "Drama"))
                .build();

        List<MovieDetailsDto> movies = List.of(movie1, movie2);
        allMoviesMock = List.of(
                MovieDto.builder()
                        .id(movie1.getId())
                        .title(movie1.getTitle())
                        .rating(movie1.getRating())
                        .year(movie1.getYear())
                        .build(),
                MovieDto.builder()
                        .id(movie2.getId())
                        .title(movie2.getTitle())
                        .rating(movie2.getRating())
                        .year(movie2.getYear())
                        .build()
        );

        moviesService = new MoviesService(movies);

    }

    @Test
    public void getAllMoviesTest() {
        List<MovieDto> moviesResponse = moviesService.getAllMovies();
        Assertions.assertThat(moviesResponse.size()).isGreaterThan(1);
    }

    @Test
    public void getMovieByIdTest() {
        MovieDetailsDto moviewDetailsResponse = moviesService.getMovieById(1L);

        assertEquals(movie1.getTitle(), moviewDetailsResponse.getTitle());
    }

    @Test
    public void getMovieByIdFailTest() {
        assertThrows(AppException.class, () -> moviesService.getMovieById(5L));
    }

    @Test
    public void createMovieTest() {
        CreateMovieDto movieDetailsDto = CreateMovieDto.builder()
                .id(3L)
                .title("The  Matrix 2")
                .description("A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.")
                .year(2001)
                .rating(5)
                .directors(List.of("Lana Wachowski", "Lilly Wachowski"))
                .actors(List.of("Keanu Reeves", "Laurence Fishburne"))
                .genres(List.of("Action", "Sci-Fi"))
                .build();
        MovieDetailsDto movieDetailsResponse = moviesService.createMovie(movieDetailsDto);
        assertEquals(movieDetailsDto.getTitle(), movieDetailsResponse.getTitle());
    }

    @Test
    public void updateMovieTest() {
        MovieDetailsDto movieDetailsDto = MovieDetailsDto.builder()
                .id(2L)
                .title("The Matrix")
                .description("A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.")
                .year(1999)
                .rating(5)
                .directors(List.of("Lana Wachowski", "Lilly Wachowski"))
                .actors(List.of("Keanu Reeves", "Laurence Fishburne"))
                .genres(List.of("Action", "Sci-Fi"))
                .build();
        MovieDetailsDto movieDetailsUpdateResponse = moviesService.updateMovie(2L, movieDetailsDto);
        MovieDetailsDto movieDetailsGetByIdResponse = moviesService.getMovieById(2L);
        assertEquals(movieDetailsDto.getTitle(), movieDetailsUpdateResponse.getTitle());
        assertEquals(movieDetailsDto.getTitle(), movieDetailsGetByIdResponse.getTitle());
    }

    @Test
    public void updateMovieFailTest() {
        MovieDetailsDto movieDetailsDto = MovieDetailsDto.builder()
                .id(2L)
                .title("The Matrix")
                .description("A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.")
                .year(1999)
                .rating(5)
                .directors(List.of("Lana Wachowski", "Lilly Wachowski"))
                .actors(List.of("Keanu Reeves", "Laurence Fishburne"))
                .genres(List.of("Action", "Sci-Fi"))
                .build();
        assertThrows(AppException.class, () -> moviesService.updateMovie(5L, movieDetailsDto));
    }

    @Test
    public void deleteMovieTest() {
        var movie = moviesService.deleteMovie(1L);

        assertEquals(movie1.getTitle(), movie.getTitle());

    }

    @Test
    public void deleteMovieFailTest() {
        assertThrows(AppException.class, () -> moviesService.deleteMovie(5L));
    }


}
