package com.williamfeliciano.sba_movies.mappers;

import com.williamfeliciano.sba_movies.dtos.ActorNameDto;
import com.williamfeliciano.sba_movies.dtos.CreateMovieDto;
import com.williamfeliciano.sba_movies.dtos.DirectorNameDto;
import com.williamfeliciano.sba_movies.dtos.MovieDetailsDto;
import com.williamfeliciano.sba_movies.dtos.MovieDto;
import com.williamfeliciano.sba_movies.entities.Actor;
import com.williamfeliciano.sba_movies.entities.Director;
import com.williamfeliciano.sba_movies.entities.Genre;
import com.williamfeliciano.sba_movies.entities.Movie;
import org.mapstruct.Mapper;

import java.util.List;

/*
* Can't delete unrelated mappings because if I do I start getting compile errors suggesting to add the
* unrelated mappings again
* */
@Mapper(componentModel = "spring",unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,uses = {GenreMapper.class,PeopleMapper.class})
public interface MovieMapper {
        Movie toMovie(MovieDetailsDto movieDetailsDto);

        Movie toMovieCreate(CreateMovieDto movieDto);

        MovieDetailsDto toMovieDetailsDto(Movie movie);

        List<Movie> toMovies(List<MovieDetailsDto> movieDetailsDtos);

        List<MovieDetailsDto> toMoviesDetailsDtos(List<Movie> movies);

        MovieDto toMovieDto(MovieDetailsDto movieDetailsDto);

        MovieDto toMovieDtoFromMovie(Movie movie);

        List<MovieDto> toMovieDtoList(List<MovieDetailsDto> movieDetailsDtos);

}


