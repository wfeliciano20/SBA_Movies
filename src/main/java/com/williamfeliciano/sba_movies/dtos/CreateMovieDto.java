package com.williamfeliciano.sba_movies.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateMovieDto {

    private long id;

    @NotNull(message = "Title is required")
    private String title;

    @NotNull(message = "Description is required")
    private String description;

    @NotNull(message = "Year is required")
    private int year;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private int rating;

    @NotNull(message = "Directors is required")
    private List<String> directors;
    // max 5 actors
    @Size(max = 5, message = "The actors list can have a maximum of 5 items")
    @NotNull(message = "Actors is required")
    private List<String> actors;

    @NotNull(message = "Genres is required")
    private List<String> genres;
}
