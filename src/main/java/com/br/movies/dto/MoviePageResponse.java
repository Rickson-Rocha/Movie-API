package com.br.movies.dto;



import java.util.List;

public record MoviePageResponse(List<MovieDto> moviesDto, Integer pageNumber, Integer pageSize, long totalElements,
                                int totalPages,boolean isLast) {}
