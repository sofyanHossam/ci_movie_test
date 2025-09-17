package com.example.movies.data.local.entity

import com.example.movies.data.remote.dto.MovieDto
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class MovieMapperTest {
    // هنعمل هنا كل test functions

    @Test
    fun `MovieDto to MovieEntity mapping is correct`(){
        // 1️⃣ تحضير بيانات dummy
        val movieDto = MovieDto(
            id = 1,
            title = "Inception",
            overview = "A mind-bending thriller",
            poster_path = "/poster.jpg",
            release_date = "2010-07-16",
            vote_average = 8.8,
            adult = false,
            original_language = "ar"
        )

        // 2️⃣ استدعاء المابّير
        val entity = movieDto.toEntity()

        // 3️⃣ assertions على كل field
        assertThat(entity.id).isEqualTo(movieDto.id)
        assertThat(entity.title).isEqualTo(movieDto.title)
        assertThat(entity.overview).isEqualTo(movieDto.overview)
        assertThat(entity.posterPath).isEqualTo(movieDto.poster_path)
        assertThat(entity.releaseDate).isEqualTo(movieDto.release_date)
        assertThat(entity.voteAverage).isEqualTo(movieDto.vote_average)
        assertThat(entity.adult).isEqualTo(movieDto.adult)
        assertThat(entity.language).isEqualTo(movieDto.original_language)
    }
    @Test
    fun `MovieEntity to MovieDto mapping is correct`() {
        // 1️⃣ تحضير بيانات dummy
        val entity = MovieEntity(
            id = 2,
            title = "Interstellar",
            overview = "Space exploration epic",
            posterPath = "/interstellar.jpg",
            releaseDate = "2014-11-07",
            voteAverage = 8.6,
            adult = false,
            language = "en"
        )

        // 2️⃣ استدعاء المابّير
        val dto = entity.toDto()

        // 3️⃣ assertions على كل field
        assertThat(dto.id).isEqualTo(entity.id)
        assertThat(dto.title).isEqualTo(entity.title)
        assertThat(dto.overview).isEqualTo(entity.overview)
        assertThat(dto.poster_path).isEqualTo(entity.posterPath)
        assertThat(dto.release_date).isEqualTo(entity.releaseDate)
        assertThat(dto.vote_average).isEqualTo(entity.voteAverage)
        assertThat(dto.adult).isEqualTo(entity.adult)
        assertThat(dto.original_language).isEqualTo(entity.language)
    }
}