package com.br.movies.controllers;


import com.br.movies.dto.MovieDto;
import com.br.movies.dto.MoviePageResponse;
import com.br.movies.exceptions.FileEmptyException;
import com.br.movies.service.MovieService;
import com.br.movies.utils.AppConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@RequestPart MultipartFile file,
                                                @RequestPart("movieDto") String movieDtoJson) throws IOException {
        if(file.isEmpty()){
            throw  new FileEmptyException("File is empty! Please  send another file");
        }
        MovieDto movieDto = convertToMovieDto(movieDtoJson);

        return new ResponseEntity<>(movieService.createMovie(movieDto, file), HttpStatus.CREATED);
    }

    private MovieDto convertToMovieDto(String movieDtoJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(movieDtoJson, MovieDto.class);
    }

    @GetMapping("{id}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable Long id) {
        MovieDto movieDto = movieService.getMovie(id);
        return new ResponseEntity<>(movieDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        List<MovieDto> movieDtos =  movieService.getAllMovies();
        return new ResponseEntity<>(movieDtos, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<MovieDto> updateMovie(
            @PathVariable Long id,
            @RequestPart("movieDto") String movieDtoJson,
            @RequestPart(required = false) MultipartFile file) throws IOException {

        MovieDto movieDto = convertToMovieDto(movieDtoJson);


        MovieDto updatedMovie = movieService.updateMovie(id, movieDto, file);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) throws IOException {
        return  ResponseEntity.ok(movieService.deleteMovie(id));
    }

    @GetMapping("movies-pagination")
    public ResponseEntity<MoviePageResponse> getMoviesWithPagination(@RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                     @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize){
        return ResponseEntity.ok(movieService.getAllMovieWithPagination(pageNumber, pageSize));
    }

    @GetMapping("movies-sort")
    public ResponseEntity<MoviePageResponse> getAllMovieWithPaginationAndSorting(@RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                                 @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                                 @RequestParam(defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
    @RequestParam(defaultValue = AppConstants.SORT_DIRECTION,required = false)String sortDirection) {

        return  ResponseEntity.ok(movieService.getAllMovieWithPaginationAndSorting(pageNumber, pageSize, sortBy, sortDirection));

    }
}
