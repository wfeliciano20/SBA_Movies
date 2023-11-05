package com.williamfeliciano.sba_movies.services;

import com.williamfeliciano.sba_movies.dtos.CreateMovieDto;
import com.williamfeliciano.sba_movies.dtos.MovieDetailsDto;
import com.williamfeliciano.sba_movies.dtos.MovieDto;
import com.williamfeliciano.sba_movies.dtos.SearchRequestDto;
import com.williamfeliciano.sba_movies.entities.Actor;
import com.williamfeliciano.sba_movies.entities.Director;
import com.williamfeliciano.sba_movies.entities.Genre;
import com.williamfeliciano.sba_movies.entities.Movie;
import com.williamfeliciano.sba_movies.exceptions.AppException;
import com.williamfeliciano.sba_movies.mappers.MovieMapper;
import com.williamfeliciano.sba_movies.repositories.MovieRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class MoviesServiceTest {

    private static List<MovieDto> allMoviesMock;
    private static List<Movie> dbMovies;
    private static Movie movieEntity1;
    private static Movie movieEntity2;
    private static Movie movieMatrix;
    private static MovieDetailsDto movie1;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private MovieMapper movieMapper;
    @InjectMocks
    private MoviesService moviesService;

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
        movieEntity1 = Movie.builder()
                .id(1L)
                .title("The Shawshank Redemption")
                .description("Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.")
                .release_year(1994)
                .rating(5)
                .directors(List.of(Director.builder().name("Frank Darabont").build()))
                .actors(List.of(Actor.builder().name("Tim Robbins").build(), Actor.builder().name("Morgan Freeman").build()))
                .genres(List.of(Genre.builder().name("Drama").build()))
                .build();
        movieEntity2 = Movie.builder()
                .id(2L)
                .title("The Godfather")
                .description("An organized crime dynasty's aging patriarch transfers control of his clandestine empire to his reluctant son.")
                .release_year(1972)
                .rating(5)
                .directors(List.of(Director.builder().name("Francis Ford Coppola").build()))
                .actors(List.of(Actor.builder().name("Marlon Brando").build(), Actor.builder().name("Al Pacino").build()))
                .genres(List.of(Genre.builder().name("Crime").build(), Genre.builder().name("Drama").build()))
                .build();
        dbMovies = List.of(movieEntity1, movieEntity2);
        movieMatrix = Movie.builder()
                .title("The Matrix")
                .description("A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.")
                .release_year(1999)
                .rating(5)
                .directors(List.of(Director.builder().name("Lana Wachowski").build()))
                .actors(List.of(Actor.builder().name("Keanu Reeves").build(), Actor.builder().name("Laurence Fishburne").build(), Actor.builder().name("Carrie-Anne Moss").build()))
                .genres(List.of(Genre.builder().name("Action").build(), Genre.builder().name("Sci-Fi").build()))
                .build();
    }

    @Test
    public void getAllMoviesTest() {
        BDDMockito.given(movieRepository.findAll()).willReturn(dbMovies);
        List<MovieDto> moviesResponse = moviesService.getAllMovies();
        Assertions.assertThat(moviesResponse.size()).isGreaterThan(0);
    }

    @Test
    public void getMovieByIdTest() {
        BDDMockito.given(movieRepository.findById(1L)).willReturn(java.util.Optional.of(movieEntity1));
        BDDMockito.given(movieMapper.toMovieDetailsDto(movieEntity1)).willReturn(movie1);
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
                .title("The Matrix 2")
                .description("A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.")
                .year(2001)
                .rating(5)
                .directors(List.of("Lana Wachowski", "Lilly Wachowski"))
                .actors(List.of("Keanu Reeves", "Laurence Fishburne"))
                .genres(List.of("Action", "Sci-Fi"))
                .build();
        MovieDetailsDto movieDetailsDto1 = MovieDetailsDto.builder()
                .id(3L)
                .title("The Matrix 2")
                .description("A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.")
                .year(2001)
                .rating(5)
                .directors(List.of("Lana Wachowski", "Lilly Wachowski"))
                .actors(List.of("Keanu Reeves", "Laurence Fishburne"))
                .genres(List.of("Action", "Sci-Fi"))
                .build();

        Movie movieToSave = Movie.builder()
                .id(3L)
                .title("The Matrix 2")
                .description("A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.")
                .release_year(2001)
                .rating(5)
                .directors(List.of(Director.builder().name("Lana Wachowski").build(), Director.builder().name("Lilly Wachowski").build()))
                .actors(List.of(Actor.builder().name("Keanu Reeves").build(), Actor.builder().name("Laurence Fishburne").build()))
                .genres(List.of(Genre.builder().name("Action").build(), Genre.builder().name("Sci-Fi").build()))
                .build();


        BDDMockito.given(movieMapper.toMovieCreate(movieDetailsDto)).willReturn(movieToSave);
        BDDMockito.given(movieRepository.save(movieToSave)).willReturn(movieToSave);
        BDDMockito.given(movieMapper.toMovieDetailsDto(movieToSave)).willReturn(movieDetailsDto1);
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
        Movie updatedMovie = Movie.builder()
                .id(2L)
                .title("The Matrix")
                .description("A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.")
                .release_year(1999)
                .rating(5)
                .directors(List.of(Director.builder().name("Lana Wachowski").build(), Director.builder().name("Lilly Wachowski").build()))
                .actors(List.of(Actor.builder().name("Keanu Reeves").build(), Actor.builder().name("Laurence Fishburne").build()))
                .genres(List.of(Genre.builder().name("Action").build(), Genre.builder().name("Sci-Fi").build()))
                .build();

        BDDMockito.given(movieRepository.findById(2L)).willReturn(java.util.Optional.of(movieEntity2));
        BDDMockito.given(movieMapper.toMovie(movieDetailsDto)).willReturn(updatedMovie);
        BDDMockito.given(movieRepository.save(updatedMovie)).willReturn(updatedMovie);
        BDDMockito.given(movieMapper.toMovieDetailsDto(updatedMovie)).willReturn(movieDetailsDto);
        MovieDetailsDto movieDetailsUpdateResponse = moviesService.updateMovie(2L, movieDetailsDto);


        assertEquals(movieDetailsDto.getTitle(), movieDetailsUpdateResponse.getTitle());
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
        BDDMockito.given(movieRepository.findById(1L)).willReturn(java.util.Optional.of(movieEntity1));
        BDDMockito.given(movieMapper.toMovieDetailsDto(movieEntity1)).willReturn(movie1);
        var movie = moviesService.deleteMovie(1L);

        assertEquals(movieEntity1.getTitle(), movie.getTitle());

    }

    @Test
    public void deleteMovieFailTest() {
        assertThrows(AppException.class, () -> moviesService.deleteMovie(5L));
    }


    @Test
    public void searchMovieByActorTest() {
        SearchRequestDto search = SearchRequestDto.builder().name("keanu").build();
        MovieDetailsDto mov = MovieDetailsDto.builder()
                .title("The Matrix")
                .description("A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.")
                .year(1999)
                .rating(5)
                .actors(List.of("Keanu Reeves", "Laurence Fishburne", "Carrie-Anne Moss"))
                .directors(List.of("Lana Wachowski"))
                .genres(List.of("Action", "Sci-Fi"))
                .build();
        BDDMockito.given(movieRepository.findFirstByActorsNameIgnoreCaseOrDirectorsNameIgnoreCase(search.getName(), search.getName())).willReturn(Optional.of(movieMatrix));
        BDDMockito.given(movieMapper.toMovieDetailsDto(movieMatrix)).willReturn(mov);
        MovieDetailsDto dbMovie = moviesService.searchMovie(search);

        Assertions.assertThat(movieMatrix.getTitle()).isEqualTo(dbMovie.getTitle());
        Assertions.assertThat(movieMatrix.getActors().size()).isEqualTo(dbMovie.getActors().size());
    }

    @Test
    public void searchMovieByDirectorTest() {
        SearchRequestDto search = SearchRequestDto.builder().name("lana").build();
        MovieDetailsDto mov = MovieDetailsDto.builder()
                .title("The Matrix")
                .description("A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.")
                .year(1999)
                .rating(5)
                .actors(List.of("Keanu Reeves", "Laurence Fishburne", "Carrie-Anne Moss"))
                .directors(List.of("Lana Wachowski"))
                .genres(List.of("Action", "Sci-Fi"))
                .build();
        BDDMockito.given(movieRepository.findFirstByActorsNameIgnoreCaseOrDirectorsNameIgnoreCase(search.getName(), search.getName())).willReturn(Optional.of(movieMatrix));
        BDDMockito.given(movieMapper.toMovieDetailsDto(movieMatrix)).willReturn(mov);
        MovieDetailsDto dbMovie = moviesService.searchMovie(search);

        Assertions.assertThat(movieMatrix.getTitle()).isEqualTo(dbMovie.getTitle());
        Assertions.assertThat(movieMatrix.getActors().size()).isEqualTo(dbMovie.getActors().size());
    }

    @Test
    public void searchMovieThrowsTest() {
        SearchRequestDto search = SearchRequestDto.builder().name("Gary").build();
        assertThrows(AppException.class, () -> moviesService.searchMovie(search));
    }
}
