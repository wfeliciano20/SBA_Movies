package com.williamfeliciano.sba_movies.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.williamfeliciano.sba_movies.config.RestExceptionHandler;
import com.williamfeliciano.sba_movies.dtos.CreateMovieDto;
import com.williamfeliciano.sba_movies.dtos.MovieDetailsDto;
import com.williamfeliciano.sba_movies.dtos.MovieDto;
import com.williamfeliciano.sba_movies.dtos.SearchRequestDto;
import com.williamfeliciano.sba_movies.exceptions.AppException;
import com.williamfeliciano.sba_movies.services.MoviesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MoviesController.class)
@Import({AopAutoConfiguration.class, RestExceptionHandler.class})
@ActiveProfiles("test")
public class MoviesControllerTest {

    private static MovieDetailsDto mockMovie1;
    private static CreateMovieDto createMovieDto;
    private static CreateMovieDto invalidCreateMovieDto;

    private static MovieDetailsDto updateMovieDto;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MoviesService moviesService;
    private List<MovieDto> mockMovieList;

    @BeforeEach
    public void setUp() {

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
        createMovieDto = CreateMovieDto.builder()
                .title("Mock Movie")
                .description("Mock Movie Description")
                .year(2022)
                .rating(5)
                .directors(List.of("Director1"))
                .actors(List.of("Actor1", "Actor2"))
                .genres(List.of("Genre1", "Genre2"))
                .build();
        updateMovieDto = MovieDetailsDto.builder()
                .title("Mock Movie")
                .description("Mock Movie Description")
                .year(2022)
                .rating(5)
                .directors(List.of("Director1"))
                .actors(List.of("Actor1", "Actor2"))
                .genres(List.of("Genre1", "Genre2"))
                .build();
        invalidCreateMovieDto = CreateMovieDto.builder()
                .title("Mock Movie")
                .description("Mock Movie Description")
                .year(2022)
                .rating(6)
                .directors(List.of("Director1"))
                .actors(List.of("Actor1", "Actor2"))
                .genres(List.of("Genre1", "Genre2"))
                .build();

        mockMovieList = new ArrayList<>(Arrays.asList(
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
        ));
    }

    @Test
    public void givenAListOfMovies_whenGetAllMoview_thenReturnsMovieList() throws Exception {

        given(moviesService.getAllMovies()).willReturn(mockMovieList);

        mockMvc.perform(get("/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(mockMovieList.size()))
                .andExpect(jsonPath("$[0].title").value(mockMovieList.get(0).getTitle()));
    }

    @Test
    public void givenAValidId_whenGetMovieById_thenReturnMovieObj() throws Exception {
        given(moviesService.getMovieById(1L)).willReturn(mockMovie1);

        mockMvc.perform(get("/movies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(mockMovie1.getTitle()));
    }

    @Test
    public void givenAnInvalidId_whenGetMovieById_thenWillReturnNotFound() throws Exception {
        given(moviesService.getMovieById(5L)).willThrow(new AppException("Movie not found", HttpStatus.NOT_FOUND));


        mockMvc.perform(get("/movies/5"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Movie not found"));

    }


    @Test
    public void givenAValidMovieObj_whenCreateMovie_thenReturnsCreatedMovieObj() throws Exception {
        given(moviesService.createMovie(createMovieDto)).willReturn(mockMovie1);

        mockMvc.perform(MockMvcRequestBuilders.post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createMovieDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(mockMovie1.getTitle()));
    }

    @Test
    public void givenInvalidMovieObj_givenCreateWithValidationError_thenProducesBadRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCreateMovieDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenAValidMovieObjAndId_whenUpdateMovie_thenReturnsUpdatedMovieObj() throws Exception {
        given(moviesService.updateMovie(2L, updateMovieDto)).willReturn(mockMovie1);

        mockMvc.perform(MockMvcRequestBuilders.put("/movies/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateMovieDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(mockMovie1.getTitle()));
    }

    @Test
    public void givenAValidMovieObjWihInvalidId_whenUpdateMovie_thenReturnsNotFound() throws Exception {
        // given precondition
        given(moviesService.updateMovie(5L, updateMovieDto)).willThrow(new AppException("Movie not found", HttpStatus.NOT_FOUND));
        // when action or behaviour
        // then expected result
        mockMvc.perform(MockMvcRequestBuilders.put("/movies/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateMovieDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Movie not found"));
    }

    @Test
    public void givenAValidId_whenDeleteMovie_thenReturnDeletedMovieObj() throws Exception {
        given(moviesService.deleteMovie(1L)).willReturn(mockMovie1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/movies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(mockMovie1.getTitle()));
    }

    @Test
    public void givenAnInvalidId_whenDeleteMovie_thenReturnsNotFound() throws Exception {
        given(moviesService.deleteMovie(5L)).willThrow(new AppException("Movie not found", HttpStatus.NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders.delete("/movies/5"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Movie not found"));
    }


    @Test
    public void givenAValidActorName_whenSearchMovie_thenReturnsOk() throws Exception {
        var search = SearchRequestDto.builder()
                .name("Actor1")
                .build();
        // given precondition
        given(moviesService.searchMovie(search)).willReturn(mockMovie1);
        // when action or behaviour
        mockMvc.perform(MockMvcRequestBuilders.post("/movies/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(search)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(mockMovie1.getTitle()));
    }


    @Test
    public void givenAValidDirectorName_whenSearchMovie_thenReturnsOk() throws Exception {
        var search = SearchRequestDto.builder()
                .name("Director1")
                .build();
        // given precondition
        given(moviesService.searchMovie(search)).willReturn(mockMovie1);
        // when action or behaviour
        mockMvc.perform(MockMvcRequestBuilders.post("/movies/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(search)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(mockMovie1.getTitle()));
    }

    @Test
    public void givenAnInValidActorName_whenSearchMovie_thenThrows() throws Exception {
        var search = SearchRequestDto.builder()
                .name("Gary")
                .build();
        // given precondition
        BDDMockito.given(moviesService.searchMovie(search)).willThrow(new AppException("Movie not found", HttpStatus.NOT_FOUND));
        // when action or behaviour
        mockMvc.perform(MockMvcRequestBuilders.post("/movies/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(search)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Movie not found"));
    }


}
