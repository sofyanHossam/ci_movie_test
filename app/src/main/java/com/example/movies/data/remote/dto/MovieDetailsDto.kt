package com.example.movies.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieDetailsDto(
    val id: Int,
    val title: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    val overview: String,
    @SerializedName("vote_average") val voteAverage: Double,
    val adult: Boolean,
    val runtime: Int?,
    val status: String,
    val tagline: String?
)
