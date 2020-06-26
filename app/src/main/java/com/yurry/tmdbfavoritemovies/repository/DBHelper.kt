package com.yurry.tmdbfavoritemovies.repository

import com.yurry.tmdbfavoritemovies.model.MovieDetail

interface DBHelper {
    suspend fun getFavoriteMovies(): List<FavoriteMovie>
    suspend fun insertMovie(movie: MovieDetail)
    suspend fun deleteMovie(id: Int)
    suspend fun isFavoriteMovie(id: Int): Boolean
}