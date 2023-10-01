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
public class MovieDetailsDto {

    private long id;

    private String title;

    private String description;

    private int year;

    private int rating;

    private List<String> directors;
    // max 5 actors
    private List<String> actors;

    private List<String> genres;
}
