package com.br.movies.service;

import com.br.movies.dto.MovieDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {

    MovieDto createMovie(MovieDto movieDto, MultipartFile file) throws IOException;
    MovieDto getMovie(Long movieId);
    List<MovieDto> getAllMovies();
    MovieDto updateMovie(Long movieId, MovieDto movieDto, MultipartFile file) throws IOException;
    String deleteMovie(Long movieId) throws IOException;


}
