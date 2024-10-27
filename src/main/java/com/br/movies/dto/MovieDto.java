package com.br.movies.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    private  Long id;


    @NotBlank(message = "Please provide movie's title")
    private String title;


    @NotBlank(message = "Please provide directory name")
    private String directory;



    @NotBlank(message = "Please provide studio name")
    private String studio;


    private Set<String> movieCast;

    private Integer releaseYear;


    @NotBlank(message = "Please provide movie's poster")
    private String poster;


    @NotBlank(message = "Please provide  poster's url")
    private String posterUrl;
}
