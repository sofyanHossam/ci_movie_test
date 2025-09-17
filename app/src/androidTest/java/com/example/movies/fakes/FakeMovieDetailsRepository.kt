package com.example.movies.fakes

import com.example.movies.domain.model.MovieDetails
import com.example.movies.domain.repository.MovieDetailsRepository


class FakeMovieDetailsRepository : MovieDetailsRepository {
    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return MovieDetails(
            id = movieId,
            title = "Fake Tenet Details",
            posterPath = null,
            releaseDate = "2020-08-22",
            overview = "This is a fake overview for testing MovieDetails.",
            voteAverage = 8.0,
            adult = false,
            runtime = 150,
            status = "Released",
            tagline = "Time Runs Out"
        )
    }
}
