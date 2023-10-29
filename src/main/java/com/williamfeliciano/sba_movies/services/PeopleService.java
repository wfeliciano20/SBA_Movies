package com.williamfeliciano.sba_movies.services;

import com.williamfeliciano.sba_movies.dtos.PersonDetailsDto;
import com.williamfeliciano.sba_movies.entities.Actor;
import com.williamfeliciano.sba_movies.entities.Director;
import com.williamfeliciano.sba_movies.exceptions.AppException;
import com.williamfeliciano.sba_movies.mappers.GenreMapper;
import com.williamfeliciano.sba_movies.mappers.PeopleMapper;
import com.williamfeliciano.sba_movies.repositories.ActorRepository;
import com.williamfeliciano.sba_movies.repositories.DirectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PeopleService {

    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final PeopleMapper peopleMapper;
    private final GenreMapper genreMapper;


    public List<String> getAllPeople() {
        List<String> people = new ArrayList<String>();
        actorRepository.findAll().forEach(actor -> people.add(peopleMapper.toActorString(actor)));
        directorRepository.findAll().forEach(director -> people.add(peopleMapper.toDirectorString(director)));
        return people;
    }

    public PersonDetailsDto getPersonById(long id, String personType) {

        if (personType.equals("actor")) {
            Actor actor = actorRepository.findById(id)
                    .orElseThrow(() -> new AppException("Person not found.", HttpStatus.NOT_FOUND));
            // how can I use the model mapper and the method toGenreString to convert the list of genres to a list of strings
            var genres = actor.getMovies().stream().map(m -> genreMapper.toGenres(m.getGenres())).flatMap(List::stream).distinct().toList();
            return PersonDetailsDto.builder()
                    .id(actor.getId())
                    .name(actor.getName())
                    .birthYear(actor.getBirthYear())
                    .country(actor.getCountry())
                    .genres(genres)
                    .build();
        } else if (personType.equals("director")) {
            Director director = directorRepository.findById(id)
                    .orElseThrow(() -> new AppException("Person not found.", HttpStatus.NOT_FOUND));

            // how can I use the model mapper and the method toGenreString to convert the list of genres to a list of strings
            var genres = director.getMovies().stream().map(m -> genreMapper.toGenres(m.getGenres())).flatMap(List::stream).distinct().toList();
            return PersonDetailsDto.builder()
                    .id(director.getId())
                    .name(director.getName())
                    .birthYear(director.getBirthYear())
                    .country(director.getCountry())
                    .genres(genres)
                    .build();

        } else {
            throw new AppException("Invalid PersonType.", HttpStatus.BAD_REQUEST);
        }
    }
}
