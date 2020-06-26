package com.yurry.tmdbfavoritemovies.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteMovieDao{
    @Query("SELECT * FROM favorite_movie_table")
    fun getFavoriteMovies():List<FavoriteMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(favoriteMovie: FavoriteMovie)

    @Query("DELETE FROM favorite_movie_table WHERE id = :id")
    fun deleteMovieById(id: Int)

    @Query("SELECT * FROM favorite_movie_table WHERE id = :id")
    fun isFavoriteMovie(id: Int): Boolean
}