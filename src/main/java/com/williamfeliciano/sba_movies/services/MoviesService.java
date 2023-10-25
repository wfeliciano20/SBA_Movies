package com.williamfeliciano.sba_movies.services;

import com.williamfeliciano.sba_movies.dtos.CreateMovieDto;
import com.williamfeliciano.sba_movies.dtos.MovieDetailsDto;
import com.williamfeliciano.sba_movies.dtos.MovieDto;
import com.williamfeliciano.sba_movies.entities.Movie;
import com.williamfeliciano.sba_movies.exceptions.AppException;
import com.williamfeliciano.sba_movies.mappers.MovieMapper;
import com.williamfeliciano.sba_movies.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MoviesService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;


    public List<MovieDto> getAllMovies() {
        List<MovieDto> movies = new ArrayList<MovieDto>();
        movieRepository.findAll().forEach(movie -> movies.add(movieMapper.toMovieDtoFromMovie(movie)));
        return movies;
    }

    public MovieDetailsDto getMovieById(long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new AppException("Movie not found.", HttpStatus.NOT_FOUND));
        return movieMapper.toMovieDetailsDto(movie);
    }

    public MovieDetailsDto createMovie(CreateMovieDto createMovieDto) {
        // Verify the movie doesn't already exist
        if (movieRepository.findByTitle(createMovieDto.getTitle()).isPresent()){
            throw new AppException("Movie already exists.", HttpStatus.BAD_REQUEST);
        }
        // Save the movie to the database
        Movie newMovie = movieRepository.save(movieMapper.toMovieCreate(createMovieDto));

        return movieMapper.toMovieDetailsDto(newMovie);
    }

    public MovieDetailsDto updateMovie(Long id, MovieDetailsDto updateMovieDto) {
       // Get the movie from db
        Movie dbMovie = movieRepository.findById(id)
                .orElseThrow(() -> new AppException("Movie not found.", HttpStatus.NOT_FOUND));

        if(dbMovie != null){
            // Update the movie
            dbMovie = movieMapper.toMovie(updateMovieDto);
            // Save the movie to the database
            Movie updatedMovie = movieRepository.save(dbMovie);
            // Convert to MovieDetailsDto and return
            return movieMapper.toMovieDetailsDto(updatedMovie);
        }
        return null;
    }

    public MovieDetailsDto deleteMovie(Long id) {
        // Get the movie from db
        Movie dbMovie = movieRepository.findById(id)
                .orElseThrow(() -> new AppException("Movie not found.", HttpStatus.NOT_FOUND));
        if(dbMovie != null){
            movieRepository.delete(dbMovie);
            return movieMapper.toMovieDetailsDto(dbMovie);
        }
        return null;
    }
}
