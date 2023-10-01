package com.williamfeliciano.sba_movies.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MovieDto {

    private long id;

    private String title;

    private int year;

    private int rating;
}
