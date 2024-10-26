package com.br.movies.repositories;

import com.br.movies.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository  extends JpaRepository<Movie, Long> {
}
