package com.example.movies.domain.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val releaseDate: String?,
    val overview: String,
    val voteAverage: Double,
    val adult: Boolean,
    val runtime: Int?,
    val status: String,
    val tagline: String?
)
