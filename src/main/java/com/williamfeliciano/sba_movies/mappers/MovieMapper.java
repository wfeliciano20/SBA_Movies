package com.williamfeliciano.sba_movies.mappers;

import com.williamfeliciano.sba_movies.dtos.CreateMovieDto;
import com.williamfeliciano.sba_movies.dtos.MovieDetailsDto;
import com.williamfeliciano.sba_movies.dtos.MovieDto;
import com.williamfeliciano.sba_movies.entities.Actor;
import com.williamfeliciano.sba_movies.entities.Director;
import com.williamfeliciano.sba_movies.entities.Genre;
import com.williamfeliciano.sba_movies.entities.Movie;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface MovieMapper {
    Movie toMovie(MovieDetailsDto movieDetailsDto);

    Movie toMovieCreate(CreateMovieDto movieDto);

    MovieDetailsDto toMovieDetailsDto(Movie movie);

    List<Movie> toMovies(List<MovieDetailsDto> movieDetailsDtos);

    List<MovieDetailsDto> toMoviesDetailsDtos(List<Movie> movies);

    MovieDto toMovieDto(MovieDetailsDto movieDetailsDto);

    MovieDto toMovieDtoFromMovie(Movie movie);

    List<MovieDto> toMovieDtoList(List<MovieDetailsDto> movieDetailsDtos);

    List<String> toActors(List<Actor> actors);

    String  toActorString(Actor actor);

    Actor toActor(String actor);

    List<Actor> toActorList(List<String> actors);

    List<String> toGenres(List<Genre> genres);

    String toGenreString(Genre genre);

    Genre toGenre(String genre);

    List<Genre> toGenreList(List<String> genres);

    List<String> toDirectors(List<Director> directors);

    String toDirectorString(Director director);

    Director toDirector(String director);

    List<Director> toDirectorList(List<String> directors);


}
