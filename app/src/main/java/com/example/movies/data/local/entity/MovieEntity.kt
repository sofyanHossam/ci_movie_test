package com.example.movies.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movies.data.remote.dto.MovieDto

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val adult: Boolean,
    val language: String
)

// Mapper: Entity -> DTO
fun MovieEntity.toDto(): MovieDto {
    return MovieDto(
        id = id,
        title = title,
        overview = overview,
        poster_path = posterPath,
        release_date = releaseDate,
        vote_average = voteAverage,
        adult = adult,
        original_language = language
    )
}

// Mapper: DTO -> Entity
fun MovieDto.toEntity(): MovieEntity {
    return MovieEntity(
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
