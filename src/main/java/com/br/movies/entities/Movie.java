package com.br.movies.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false,length = 200)
    @NotBlank(message = "Please provide movie's title")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Please provide directory name")
    private String directory;


    @Column(nullable = false,length = 200)
    @NotBlank(message = "Please provide studio name")
    private String studio;


    @ElementCollection
    @Column(name = "movie_cast")
    private Set<String> movieCast;

    @Column(nullable = false,length = 200)
    private Integer releaseYear;

    @Column(nullable = false,length = 200)
    @NotBlank(message = "Please provide movie's poster")
    private String poster;

}
