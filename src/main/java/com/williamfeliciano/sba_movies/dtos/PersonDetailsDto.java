package com.williamfeliciano.sba_movies.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonDetailsDto {

    private long id;

   private String name;

    private int birthYear;

    private String country;

    private List<String> genres;

}
