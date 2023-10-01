package com.williamfeliciano.sba_movies.controllers;


import com.williamfeliciano.sba_movies.dtos.MovieDetailsDto;
import com.williamfeliciano.sba_movies.dtos.MovieDto;
import com.williamfeliciano.sba_movies.services.MoviesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(MoviesController.class)
public class MoviesControllerTest {

    private static MovieDetailsDto mockMovie1;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MoviesService moviesService;
    private List<MovieDto> mockMovieList;

    @BeforeEach
    public void setUp() {

        // Initialize mock data
        mockMovie1 = MovieDetailsDto.builder()
                .id(1L)
                .title("Mock Movie")
                .description("Mock Movie Description")
                .year(2022)
                .rating(5)
                .directors(List.of("Director1"))
                .actors(List.of("Actor1", "Actor2"))
                .genres(List.of("Genre1", "Genre2"))
                .build();
        MovieDetailsDto mockMovie2 = MovieDetailsDto.builder()
                .id(2L)
                .title("Mock Movie 2")
                .description("Mock Movie 2 Description")
                .year(2023)
                .rating(4)
                .directors(List.of("Director2"))
                .actors(List.of("Actor3", "Actor4"))
                .genres(List.of("Genre3", "Genre4"))
                .build();

        mockMovieList = List.of(
                MovieDto.builder()
                        .id(mockMovie1.getId())
                        .title(mockMovie1.getTitle())
                        .rating(mockMovie1.getRating())
                        .year(mockMovie1.getYear())
                        .build(),
                MovieDto.builder()
                        .id(mockMovie2.getId())
                        .title(mockMovie2.getTitle())
                        .rating(mockMovie2.getRating())
                        .year(mockMovie2.getYear())
                        .build()
        );
    }

    @Test
    public void testGetMovies() throws Exception {
        // Specify the behavior of the moviesService mock
        when(moviesService.getAllMovies()).thenReturn(mockMovieList);

        mockMvc.perform(MockMvcRequestBuilders.get("/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(mockMovieList.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(mockMovieList.get(0).getTitle()));
    }

    @Test
    public void testGetMovieById() throws Exception {
        when(moviesService.getMovieById(1L)).thenReturn(mockMovie1);

        mockMvc.perform(MockMvcRequestBuilders.get("/movies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(mockMovie1.getTitle()));
    }
}
