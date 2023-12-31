package com.williamfeliciano.sba_movies.controllers;

import com.williamfeliciano.sba_movies.dtos.PersonDetailsDto;
import com.williamfeliciano.sba_movies.services.PeopleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PeopleController {

    private final PeopleService peopleService;

    @GetMapping("/people")
    public ResponseEntity<List<String>> getAllPeople() {
        return ResponseEntity.ok(peopleService.getAllPeople());
    }

    @GetMapping("/people/{personType}/{id}")
    public ResponseEntity<PersonDetailsDto> getPersonById(@PathVariable long id, @PathVariable String personType) {
        return ResponseEntity.ok(peopleService.getPersonById(id, personType));
    }

}
