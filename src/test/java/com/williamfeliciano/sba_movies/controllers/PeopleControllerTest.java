package com.williamfeliciano.sba_movies.controllers;

import com.williamfeliciano.sba_movies.config.RestExceptionHandler;
import com.williamfeliciano.sba_movies.dtos.PersonDetailsDto;
import com.williamfeliciano.sba_movies.exceptions.AppException;
import com.williamfeliciano.sba_movies.services.PeopleService;
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

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PeopleController.class)
@Import({AopAutoConfiguration.class, RestExceptionHandler.class})
@ActiveProfiles("test")
public class PeopleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeopleService peopleService;


    @Test
    public void givenAListOfPeople_whenGetAllPeople_thenReturnsListOfPeople() throws Exception {
        // given precondition
        var people = List.of("Keanu Reeves", "Laurence Fishburne", "Carrie-Anne Moss", "Heath Ledger", "Cristian Bale", "Ellen Page");
        BDDMockito.given(peopleService.getAllPeople()).willReturn(people);

        mockMvc.perform(MockMvcRequestBuilders.get("/people").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(people.size()))
                .andExpect(jsonPath("$[0]").value(people.get(0)));
    }

    @Test
    public void givenAValidIdOfActorAndValidPersonType_whengetPersonById_thenReturnAPersonDetails() throws Exception {
        // given precondition
        var id = 1L;
        var personType = "actor";
        PersonDetailsDto personDetails = PersonDetailsDto.builder().id(1L).name("Keanu Reeves").birthYear(0).country(null).genres(List.of("Action", "Sci-Fi")).build();
        BDDMockito.given(peopleService.getPersonById(id,personType)).willReturn(personDetails);

        mockMvc.perform(MockMvcRequestBuilders.get("/people/actor/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(personDetails.getId()))
                .andExpect(jsonPath("$.name").value(personDetails.getName()))
                .andExpect(jsonPath("$.birthYear").value(personDetails.getBirthYear()))
                .andExpect(jsonPath("$.country").value(personDetails.getCountry()))
                .andExpect(jsonPath("$.genres.size()").value(personDetails.getGenres().size()))
                .andExpect(jsonPath("$.genres[0]").value(personDetails.getGenres().get(0)))
                .andExpect(jsonPath("$.genres[1]").value(personDetails.getGenres().get(1)));
    }

    @Test
    public void givenAValidIdOfDirectorAndValidPersonType_whengetPersonById_thenReturnAPersonDetails() throws Exception {
        // given precondition
        var id = 1L;
        var personType = "director";
        PersonDetailsDto personDetails = PersonDetailsDto.builder().id(1L).name("Lana Wachowski").birthYear(0).country(null).genres(List.of("Action", "Sci-Fi")).build();
        BDDMockito.given(peopleService.getPersonById(id,personType)).willReturn(personDetails);

        mockMvc.perform(MockMvcRequestBuilders.get("/people/director/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(personDetails.getId()))
                .andExpect(jsonPath("$.name").value(personDetails.getName()))
                .andExpect(jsonPath("$.birthYear").value(personDetails.getBirthYear()))
                .andExpect(jsonPath("$.country").value(personDetails.getCountry()))
                .andExpect(jsonPath("$.genres.size()").value(personDetails.getGenres().size()))
                .andExpect(jsonPath("$.genres[0]").value(personDetails.getGenres().get(0)))
                .andExpect(jsonPath("$.genres[1]").value(personDetails.getGenres().get(1)));
    }

    @Test
    public void givenAnInvalidActorIdAndAValidPersonType_whenGetPersonByID_thenThrows() throws Exception {
        // given precondition
        var id = 999L;
        var personType = "actor";
        BDDMockito.given(peopleService.getPersonById(id,personType)).willThrow(new AppException("Person not found.", HttpStatus.NOT_FOUND));
        mockMvc.perform(MockMvcRequestBuilders.get("/people/actor/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Person not found."));
    }

    @Test
    public void givenAnInvalidDirectorIdAndAValidPersonType_whenGetPersonByID_thenThrows() throws Exception {
        // given precondition
        var id = 999L;
        var personType = "director";
        BDDMockito.given(peopleService.getPersonById(id,personType)).willThrow(new AppException("Person not found.", HttpStatus.NOT_FOUND));
        mockMvc.perform(MockMvcRequestBuilders.get("/people/director/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Person not found."));
    }

    @Test
    public void givenAValidActorIdAndAnInValidPersonType_whenGetPersonByID_thenThrows() throws Exception {
        // given precondition
        var id = 1L;
        var personType = "invalid";
        BDDMockito.given(peopleService.getPersonById(id,personType)).willThrow(new AppException("Invalid PersonType.", HttpStatus.BAD_REQUEST));
        mockMvc.perform(MockMvcRequestBuilders.get("/people/invalid/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid PersonType."));
    }
}
