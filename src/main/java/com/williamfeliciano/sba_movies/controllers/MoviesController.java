package com.williamfeliciano.sba_movies.controllers;

import com.williamfeliciano.sba_movies.dtos.MovieDetailsDto;
import com.williamfeliciano.sba_movies.dtos.MovieDto;
import com.williamfeliciano.sba_movies.services.MoviesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MoviesController {

    private final MoviesService moviesService;

    @GetMapping("/movies")
    public ResponseEntity<List<MovieDto>> GetMovies() {
        return ResponseEntity.ok(moviesService.getAllMovies());
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<MovieDetailsDto> GetMovieById(@PathVariable long id) {
        return ResponseEntity.ok(moviesService.getMovieById(id));
    }
}
