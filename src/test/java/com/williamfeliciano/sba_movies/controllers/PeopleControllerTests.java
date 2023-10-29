package com.williamfeliciano.sba_movies.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.williamfeliciano.sba_movies.config.RestExceptionHandler;
import com.williamfeliciano.sba_movies.dtos.GetPersonDetailsRequestDto;
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
public class PeopleControllerTests {

    @Autowired
    private ObjectMapper objectMapper;

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
        var getPersonDetailsRequestDto = GetPersonDetailsRequestDto.builder().id(1L).personType("actor").build();
        PersonDetailsDto personDetails = PersonDetailsDto.builder().id(1L).name("Keanu Reeves").birthYear(0).country(null).genres(List.of("Action", "Sci-Fi")).build();
        BDDMockito.given(peopleService.getPersonById(getPersonDetailsRequestDto)).willReturn(personDetails);

        mockMvc.perform(MockMvcRequestBuilders.get("/people/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getPersonDetailsRequestDto)))
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
        var getPersonDetailsRequestDto = GetPersonDetailsRequestDto.builder().id(1L).personType("director").build();
        PersonDetailsDto personDetails = PersonDetailsDto.builder().id(1L).name("Lana Wachowski").birthYear(0).country(null).genres(List.of("Action", "Sci-Fi")).build();
        BDDMockito.given(peopleService.getPersonById(getPersonDetailsRequestDto)).willReturn(personDetails);

        mockMvc.perform(MockMvcRequestBuilders.get("/people/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getPersonDetailsRequestDto)))
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
        var getPersonDetailsRequestDto = GetPersonDetailsRequestDto.builder().id(999L).personType("actor").build();
        BDDMockito.given(peopleService.getPersonById(getPersonDetailsRequestDto)).willThrow(new AppException("Person not found.", HttpStatus.NOT_FOUND));
        mockMvc.perform(MockMvcRequestBuilders.get("/people/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getPersonDetailsRequestDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Person not found."));
    }

    @Test
    public void givenAnInvalidDirectorIdAndAValidPersonType_whenGetPersonByID_thenThrows() throws Exception {
        // given precondition
        var getPersonDetailsRequestDto = GetPersonDetailsRequestDto.builder().id(999L).personType("director").build();
        BDDMockito.given(peopleService.getPersonById(getPersonDetailsRequestDto)).willThrow(new AppException("Person not found.", HttpStatus.NOT_FOUND));
        mockMvc.perform(MockMvcRequestBuilders.get("/people/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getPersonDetailsRequestDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Person not found."));
    }

    @Test
    public void givenAValidActorIdAndAnInValidPersonType_whenGetPersonByID_thenThrows() throws Exception {
        // given precondition
        var getPersonDetailsRequestDto = GetPersonDetailsRequestDto.builder().id(1L).personType("invalid").build();
        BDDMockito.given(peopleService.getPersonById(getPersonDetailsRequestDto)).willThrow(new AppException("Invalid PersonType.", HttpStatus.BAD_REQUEST));
        mockMvc.perform(MockMvcRequestBuilders.get("/people/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getPersonDetailsRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid PersonType."));
    }
}
