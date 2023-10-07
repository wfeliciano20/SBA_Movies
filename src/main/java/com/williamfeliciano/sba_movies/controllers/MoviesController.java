package com.williamfeliciano.sba_movies.controllers;

import com.williamfeliciano.sba_movies.dtos.CreateMovieDto;
import com.williamfeliciano.sba_movies.dtos.MovieDetailsDto;
import com.williamfeliciano.sba_movies.dtos.MovieDto;
import com.williamfeliciano.sba_movies.services.MoviesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MoviesController {

    private final MoviesService moviesService;

    @GetMapping("/movies")
    public ResponseEntity<List<MovieDto>> getMovies() {
        return ResponseEntity.ok(moviesService.getAllMovies());
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<MovieDetailsDto> getMovieById(@PathVariable long id) {
        return ResponseEntity.ok(moviesService.getMovieById(id));
    }

    @PostMapping("/movies")
    public ResponseEntity<MovieDetailsDto> createMovie(@Valid @RequestBody CreateMovieDto createMovieDto) {
        MovieDetailsDto createdMovie = moviesService.createMovie(createMovieDto);
        return ResponseEntity.created(URI.create("/movies/" + createdMovie.getId())).body(createdMovie);
    }

    @PutMapping("/movies/{id}")
    public ResponseEntity<MovieDetailsDto>   updateMovie(@PathVariable Long id, @Valid @RequestBody MovieDetailsDto updateMovieDto){
        return ResponseEntity.ok(moviesService.updateMovie(id, updateMovieDto));
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<MovieDetailsDto> deleteMovie(@PathVariable Long id){
        var movie = moviesService.deleteMovie(id);
        return ResponseEntity.ok(movie);
    }
}
