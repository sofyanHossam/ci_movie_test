package com.example.movies.fakes

import com.example.movies.data.remote.dto.MovieDto
import com.example.movies.data.remote.dto.MovieResponse
import com.example.movies.domain.repo.MovieRepository

class FakeMovieRepository : MovieRepository {
    override suspend fun getPopularMovies(page: Int): MovieResponse {
        return MovieResponse(
            page = 1,
            results = listOf(
                MovieDto(
                    id = 1,
                    title = "Fake Tenet",
                    overview = "Fake Overview",
                    poster_path = null,
                    release_date = "2020-01-01",
                    vote_average = 7.5,
                    adult = false,
                    original_language = "en"
                )
            ),
            total_pages = 1,
            total_results = 1
        )
    }
}
