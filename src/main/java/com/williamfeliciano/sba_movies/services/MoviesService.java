package com.williamfeliciano.sba_movies.services;

import com.williamfeliciano.sba_movies.dtos.MovieDetailsDto;
import com.williamfeliciano.sba_movies.dtos.MovieDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class MoviesService {
    // Creating MovieDto objects with real movie data
    private final List<MovieDetailsDto> movies;

    public MoviesService() {
        MovieDetailsDto movie1 = MovieDetailsDto.builder()
                .id(1)
                .title("The Shawshank Redemption")
                .description("Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.")
                .year(1994)
                .rating(5)
                .directors(List.of("Frank Darabont"))
                .actors(List.of("Tim Robbins", "Morgan Freeman"))
                .genres(List.of("Drama"))
                .build();

        MovieDetailsDto movie2 = MovieDetailsDto.builder()
                .id(2)
                .title("The Godfather")
                .description("An organized crime dynasty's aging patriarch transfers control of his clandestine empire to his reluctant son.")
                .year(1972)
                .rating(5)
                .directors(List.of("Francis Ford Coppola"))
                .actors(List.of("Marlon Brando", "Al Pacino"))
                .genres(List.of("Crime", "Drama"))
                .build();

        MovieDetailsDto movie3 = MovieDetailsDto.builder()
                .id(3)
                .title("The Dark Knight")
                .description("When the menace known as The Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.")
                .year(2008)
                .rating(4)
                .directors(List.of("Christopher Nolan"))
                .actors(List.of("Christian Bale", "Heath Ledger"))
                .genres(List.of("Action", "Crime", "Drama"))
                .build();

        MovieDetailsDto movie4 = MovieDetailsDto.builder()
                .id(4)
                .title("Pulp Fiction")
                .description("The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.")
                .year(1994)
                .rating(5)
                .directors(List.of("Quentin Tarantino"))
                .actors(List.of("John Travolta", "Samuel L. Jackson"))
                .genres(List.of("Crime", "Drama"))
                .build();

        MovieDetailsDto movie5 = MovieDetailsDto.builder()
                .id(5)
                .title("Inception")
                .description("A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.")
                .year(2010)
                .rating(4)
                .directors(List.of("Christopher Nolan"))
                .actors(List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt"))
                .genres(List.of("Action", "Adventure", "Sci-Fi"))
                .build();

        this.movies = List.of(movie1, movie2, movie3, movie4, movie5);
    }
    public MoviesService(List<MovieDetailsDto> movies){
        this.movies = movies;
    }
    private List<MovieDto> convertToMovieDtoList(List<MovieDetailsDto> movies) {
        return movies.stream()
                .map(m -> MovieDto.builder()
                        .id(m.getId())
                        .title(m.getTitle())
                        .rating(m.getRating())
                        .year(m.getYear())
                        .build())
                .collect(Collectors.toList());
    }

    public List<MovieDto> getAllMovies() {
        return convertToMovieDtoList(movies);
    }

    public MovieDetailsDto getMovieById(long id) {
        return movies.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Movie not found."));
    }

}
