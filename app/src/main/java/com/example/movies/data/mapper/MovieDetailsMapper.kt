package com.example.movies.data.mapper


import com.example.movies.data.remote.dto.MovieDetailsDto
import com.example.movies.domain.model.MovieDetails

fun MovieDetailsDto.toDomain(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title,
        posterPath = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        releaseDate = releaseDate,
        overview = overview,
        voteAverage = voteAverage,
        adult = adult,
        runtime = runtime,
        status = status,
        tagline = tagline
    )
}
