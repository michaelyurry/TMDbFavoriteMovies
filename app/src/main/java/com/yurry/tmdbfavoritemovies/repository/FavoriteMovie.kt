package com.yurry.tmdbfavoritemovies.repository

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movie_table")
data class FavoriteMovie (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id") val id: Int,

    @ColumnInfo(name = "popularity") val popularity : String,
    @ColumnInfo(name = "vote_count") val voteCount : Int,
    @ColumnInfo(name = "video") val video: Boolean,
    @ColumnInfo(name = "poster_path") val posterPath: String,
    @ColumnInfo(name = "adult") val adult: Boolean,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String,
    @ColumnInfo(name = "original_language") val originalLanguage: String,
    @ColumnInfo(name = "original_title") val originalTitle: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "release_date") val releaseDate: String
)