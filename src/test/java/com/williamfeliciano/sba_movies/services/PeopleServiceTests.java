package com.williamfeliciano.sba_movies.services;

import com.williamfeliciano.sba_movies.dtos.GetPersonDetailsRequestDto;
import com.williamfeliciano.sba_movies.entities.Actor;
import com.williamfeliciano.sba_movies.entities.Director;
import com.williamfeliciano.sba_movies.entities.Genre;
import com.williamfeliciano.sba_movies.entities.Movie;
import com.williamfeliciano.sba_movies.exceptions.AppException;
import com.williamfeliciano.sba_movies.mappers.MovieMapper;
import com.williamfeliciano.sba_movies.repositories.ActorRepository;
import com.williamfeliciano.sba_movies.repositories.DirectorRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class PeopleServiceTests {

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private DirectorRepository directorRepository;

    @Mock
    private MovieMapper movieMapper;

    @InjectMocks
    private PeopleService peopleService;

    @BeforeAll
    public static void setUp() {

    }

    @Test
    public void givenArequest_whenGetAllPeople_thenReturnAllPeopleList() {
        // given precondition
        BDDMockito.given(actorRepository.findAll()).willReturn(List.of(Actor.builder().name("Keanu Reeves").build(), Actor.builder().name("Laurence Fishburne").build(), Actor.builder().name("Carrie-Anne Moss").build(), Actor.builder().name("Heath Ledger").build(), Actor.builder().name("Cristian Bale").build(), Actor.builder().name("Ellen Page").build()));
        BDDMockito.given(directorRepository.findAll()).willReturn(List.of(Director.builder().name("Lana Wachowski").build(), Director.builder().name("Christopher Nolan").build(), Director.builder().name("Pete Docter").build()));
        BDDMockito.given(movieMapper.toActorString(Actor.builder().name("Keanu Reeves").build())).willReturn("Keanu Reeves");
        BDDMockito.given(movieMapper.toActorString(Actor.builder().name("Laurence Fishburne").build())).willReturn("Laurence Fishburne");
        BDDMockito.given(movieMapper.toActorString(Actor.builder().name("Carrie-Anne Moss").build())).willReturn("Carrie-Anne Moss");
        BDDMockito.given(movieMapper.toActorString(Actor.builder().name("Heath Ledger").build())).willReturn("Heath Ledger");
        BDDMockito.given(movieMapper.toActorString(Actor.builder().name("Cristian Bale").build())).willReturn("Cristian Bale");
        BDDMockito.given(movieMapper.toActorString(Actor.builder().name("Ellen Page").build())).willReturn("Ellen Page");
        BDDMockito.given(movieMapper.toDirectorString(Director.builder().name("Lana Wachowski").build())).willReturn("Lana Wachowski");
        BDDMockito.given(movieMapper.toDirectorString(Director.builder().name("Christopher Nolan").build())).willReturn("Christopher Nolan");
        BDDMockito.given(movieMapper.toDirectorString(Director.builder().name("Pete Docter").build())).willReturn("Pete Docter");
        // when action or behaviour
        List<String> people = List.of("Keanu Reeves", "Laurence Fishburne", "Carrie-Anne Moss", "Heath Ledger", "Cristian Bale", "Ellen Page", "Lana Wachowski", "Christopher Nolan", "Pete Docter");
        var result = peopleService.getAllPeople();
        // then expected result
        Assertions.assertThat(result.size()).isEqualTo(people.size());
        Assertions.assertThat(result).isEqualTo(people);
    }

    @Test
    public void givenAValidActorId_whenGetPersonByID_thenReturnPersonDetails() {
        // given precondition
        var getPersonDetailsRequestDto = GetPersonDetailsRequestDto.builder().id(1L).personType("actor").build();
        var movie = Movie.builder()
                .title("The Matrix")
                .description("A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.")
                .release_year(1999)
                .rating(5)
                .directors(List.of(Director.builder().name("Lana Wachowski").build()))
                .actors(List.of(Actor.builder().name("Keanu Reeves").build(), Actor.builder().name("Laurence Fishburne").build(), Actor.builder().name("Carrie-Anne Moss").build()))
                .genres(List.of(Genre.builder().name("Action").build(), Genre.builder().name("Sci-Fi").build()))
                .build();
        BDDMockito.given(actorRepository.findById(1L)).willReturn(java.util.Optional.of(Actor.builder().id(1L).name("Keanu Reeves").movies(List.of(movie)).build()));
        BDDMockito.given(movieMapper.toGenres(List.of(Genre.builder().name("Action").build(), Genre.builder().name("Sci-Fi").build()))).willReturn(List.of("Action", "Sci-Fi"));
        // when action or behaviour
        var result = peopleService.getPersonById(getPersonDetailsRequestDto);
        // then expected result
        Assertions.assertThat(result.getId()).isEqualTo(1L);
        Assertions.assertThat(result.getName()).isEqualTo("Keanu Reeves");
        Assertions.assertThat(result.getGenres().size()).isEqualTo(2);
    }

    @Test
    public void givenAValidDirectorId_whenGetPersonByID_thenReturnPersonDetails() {
        // given precondition
        var getPersonDetailsRequestDto = GetPersonDetailsRequestDto.builder().id(1L).personType("director").build();
        var movie = Movie.builder()
                .title("The Matrix")
                .description("A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.")
                .release_year(1999)
                .rating(5)
                .directors(List.of(Director.builder().name("Lana Wachowski").build()))
                .actors(List.of(Actor.builder().name("Keanu Reeves").build(), Actor.builder().name("Laurence Fishburne").build(), Actor.builder().name("Carrie-Anne Moss").build()))
                .genres(List.of(Genre.builder().name("Action").build(), Genre.builder().name("Sci-Fi").build()))
                .build();
        BDDMockito.given(directorRepository.findById(1L)).willReturn(java.util.Optional.of(Director.builder().id(1L).name("Lana Wachowski").movies(List.of(movie)).build()));
        BDDMockito.given(movieMapper.toGenres(List.of(Genre.builder().name("Action").build(), Genre.builder().name("Sci-Fi").build()))).willReturn(List.of("Action", "Sci-Fi"));
        // when action or behaviour
        var result = peopleService.getPersonById(getPersonDetailsRequestDto);
        // then expected result
        Assertions.assertThat(result.getId()).isEqualTo(1L);
        Assertions.assertThat(result.getName()).isEqualTo("Lana Wachowski");
        Assertions.assertThat(result.getGenres().size()).isEqualTo(2);
    }

    @Test
    public void givenAnInvalidIDANDAValidPersonType_whenGetPersonByID_thenThrows() {
        // given precondition
        var getPersonDetailsRequestDto = GetPersonDetailsRequestDto.builder().id(999L).personType("director").build();
        assertThrows(AppException.class, () -> peopleService.getPersonById(getPersonDetailsRequestDto));
    }

    @Test
    public void givenAValidIDAndAnInvalidPersonType_whenGetPersonByID_thenThrows() {
        // given precondition
        var getPersonDetailsRequestDto = GetPersonDetailsRequestDto.builder().id(1L).personType("invalid").build();
        assertThrows(AppException.class, () -> peopleService.getPersonById(getPersonDetailsRequestDto));
    }


}
