package com.williamfeliciano.sba_movies.mappers;

import com.williamfeliciano.sba_movies.dtos.ActorNameDto;
import com.williamfeliciano.sba_movies.dtos.DirectorNameDto;
import com.williamfeliciano.sba_movies.entities.Actor;
import com.williamfeliciano.sba_movies.entities.Director;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface PeopleMapper {

    List<String> toDirectors(List<Director> directors);

    String toDirectorString(Director director);

    DirectorNameDto toDirectorNameDto(Director director);

    Director toDirector(String director);

    List<Director> toDirectorList(List<String> directors);


    List<String> toActors(List<Actor> actors);

    String  toActorString(Actor actor);

    ActorNameDto toActorNameDto(Actor actor);

    Actor toActor(String actor);

    List<Actor> toActorList(List<String> actors);
}
