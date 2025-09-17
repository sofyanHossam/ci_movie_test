package com.example.movies.data.mapper

import com.example.movies.data.remote.dto.MovieDto
import com.example.movies.domain.model.Movie

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = poster_path,
        releaseDate = release_date,
        voteAverage = vote_average,
        adult = adult,
        language = original_language
    )
}

