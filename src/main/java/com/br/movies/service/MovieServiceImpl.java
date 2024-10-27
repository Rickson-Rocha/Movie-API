package com.br.movies.service;

import com.br.movies.dto.MovieDto;
import com.br.movies.entities.Movie;
import com.br.movies.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    @Value("${project.poster}")
    private String path;

    @Value("${base.url}")
    private  String baseUrl;

    private final MovieRepository movieRepository;

    private final FileService fileService;



    @Override
    public MovieDto createMovie(MovieDto movieDto, MultipartFile file) throws IOException {
        //1. upload file
        if(Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
            throw  new RuntimeException("File already exists! Please choose a different filename");
        }
       String uploadedFileName =  fileService.uploadFile(path,file);

        //2. set the value of field 'post' as filename
        movieDto.setPoster(uploadedFileName);


        //3. map dto to Movie object

        Movie movie = new Movie(
                null,
                movieDto.getTitle(),
                movieDto.getDirectory(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );
        //4. save the movie object - > saved movie object
        Movie savedMovie = movieRepository.save(movie);
        //5. generated poster url
        String posterUrl = baseUrl + "/files/"  + uploadedFileName;

        MovieDto response = new MovieDto(
                savedMovie.getId(),
                savedMovie.getTitle(),
                savedMovie.getDirectory(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getPoster(),
                posterUrl
        );
        //6. map Movie object to MovieDto  and return it
        return response;
    }

    @Override
    public MovieDto getMovie(Long movieId) {
        //1. check data in DB and if exists, fetch the data of given ID
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));

        //2. generate poster url
        String posterUrl = baseUrl + "/files/"  + movie.getPoster();

        //3. map to MovieDto object and return it
        MovieDto response = new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getDirectory(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl
        );
        return response;
    }

    @Override
    public List<MovieDto> getAllMovies() {
        //1. fetch all data from DB
        List<Movie> movies = movieRepository.findAll();

        List<MovieDto> movieDtos = new ArrayList<>();
        for(Movie movie : movies) {
            String posterUrl = baseUrl + "/files/"  + movie.getPoster();
            MovieDto movieDto = new MovieDto(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getDirectory(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl
            );
            movieDtos.add(movieDto);
        }

        //2. iterate  through the list, and generate posterUrl for each movie
        // and map to MovieDto obj



        return movieDtos;
    }

    @Override
    public MovieDto updateMovie(Long movieId, MovieDto movieDto, MultipartFile file) throws IOException {
        //1.check if movie object exists with given movieId
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));

        //2.if file is null, do nothing
        // if file is not null, then delete existing file associated with the record,
        // and upload the new file
        String fileName = movie.getPoster();
        if (file !=null){
            Files.deleteIfExists(Paths.get(path + File.separator + fileName));
            fileName = fileService.uploadFile(path,file);
        }
        //3.  set movieDto's poster value, according step 2
        movieDto.setPoster(fileName);
        //4. map it to movie Object
        Movie movieSaved =  new Movie(
                movie.getId(),
                movieDto.getTitle(),
                movieDto.getDirectory(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster());

        //5. save the movie object -> return saved object
        Movie updatedMovie = movieRepository.save(movieSaved);
        //6. generate postUrl for it
        String posterUrl = baseUrl + "/files/"  + fileName;

        //7. map to MovieDto and return it
        MovieDto response = new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getDirectory(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl
        );
        return response;
    }

    @Override
    public String deleteMovie(Long movieId) throws IOException {
        MovieDto movie = getMovie(movieId);

        Files.deleteIfExists(Paths.get(path + File.separator + movie.getPoster()));

        Movie movieSaved =  new Movie(

                movie.getId(),
                movie.getTitle(),
                movie.getDirectory(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster()
        );

        movieRepository.delete(movieSaved);

        return "Movie deleted id: "+movieSaved.getId();

    }
}
