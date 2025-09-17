package com.example.movies.data.remote.dto

data class MovieResponse(
    val page: Int,
    val results: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
)

data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val release_date: String?,
    val vote_average: Double,
    val adult: Boolean,
    val original_language: String
)
