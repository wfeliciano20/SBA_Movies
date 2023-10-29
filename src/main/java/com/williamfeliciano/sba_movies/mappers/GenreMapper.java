package com.williamfeliciano.sba_movies.mappers;

import com.williamfeliciano.sba_movies.entities.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenreMapper {


    List<String> toGenres(List<Genre> genres);

    String toGenreString(Genre genre);

    Genre toGenre(String genre);

    List<Genre> toGenreList(List<String> genres);
}
