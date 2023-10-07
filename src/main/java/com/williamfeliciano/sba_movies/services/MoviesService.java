package com.williamfeliciano.sba_movies.services;

import com.williamfeliciano.sba_movies.dtos.CreateMovieDto;
import com.williamfeliciano.sba_movies.dtos.MovieDetailsDto;
import com.williamfeliciano.sba_movies.dtos.MovieDto;
import com.williamfeliciano.sba_movies.exceptions.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MoviesService {
    // Creating MovieDto objects with real movie data
    private List<MovieDetailsDto> movies;

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

        this.movies = new ArrayList<>(Arrays.asList(movie1, movie2, movie3, movie4, movie5));
    }

    public MoviesService(List<MovieDetailsDto> movies) {
        this.movies = new ArrayList<>(movies);
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
                .orElseThrow(() -> new AppException("Movie not found.", HttpStatus.NOT_FOUND));
    }

    public MovieDetailsDto createMovie(CreateMovieDto createMovieDto) {
        // Verify the movie doesn't already exist
        if (movies.stream().anyMatch(m -> m.getTitle().equals(createMovieDto.getTitle()))) {
            throw new AppException("Movie already exists.", HttpStatus.BAD_REQUEST);
        }
        // Create the MovieDetailsDto object
        MovieDetailsDto newMovie = MovieDetailsDto.builder()
                .id(movies.size() + 1)
                .title(createMovieDto.getTitle())
                .description(createMovieDto.getDescription())
                .year(createMovieDto.getYear())
                .rating(createMovieDto.getRating())
                .directors(createMovieDto.getDirectors())
                .actors(createMovieDto.getActors())
                .genres(createMovieDto.getGenres())
                .build();

        // add the new movie to the movies list
        movies.add(newMovie);

        return newMovie;
    }

    public MovieDetailsDto updateMovie(Long id, MovieDetailsDto updateMovieDto) {
        MovieDetailsDto movie = getMovieById(id);

        if(movie != null){
            // Update the movie
            movie.setTitle(updateMovieDto.getTitle());
            movie.setDescription(updateMovieDto.getDescription());
            movie.setYear(updateMovieDto.getYear());
            movie.setRating(updateMovieDto.getRating());
            movie.setDirectors(updateMovieDto.getDirectors());
            movie.setActors(updateMovieDto.getActors());
            movie.setGenres(updateMovieDto.getGenres());

            // Update the movie in the list
            movies = movies.stream().map(m -> {
                if (m.getId() == id) {
                    m = movie;
                }
                return m;
            }).collect(Collectors.toList());

            return movie;
        }
        return null;
    }



    public MovieDetailsDto deleteMovie(Long id) {
        var movie = getMovieById(id);
        if(movie != null){
            movies.remove(movie);
            return movie;
        }
        else{
            return null;
        }
    }
}
