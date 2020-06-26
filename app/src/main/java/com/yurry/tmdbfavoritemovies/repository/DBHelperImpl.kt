package com.yurry.tmdbfavoritemovies.repository

import com.yurry.tmdbfavoritemovies.model.MovieDetail

class DBHelperImpl(private val favoriteMovieDB: FavoriteMovieDB) : DBHelper {
    private fun convertModelToEntity(movieDetail: MovieDetail): FavoriteMovie {
        return FavoriteMovie(
            movieDetail.id,
            movieDetail.popularity.toString(),
            movieDetail.voteCount.toInt(),
            movieDetail.video,
            movieDetail.posterPath,
            movieDetail.adult,
            movieDetail.backdropPath,
            movieDetail.originalLanguage,
            movieDetail.originalTitle,
            movieDetail.title,
            movieDetail.voteAverage,
            movieDetail.overview,
            movieDetail.releaseDate
        )
    }

    override suspend fun getFavoriteMovies(): List<FavoriteMovie> {
        return favoriteMovieDB.favoriteMovieDao().getFavoriteMovies()
    }

    override suspend fun insertMovie(movie: MovieDetail) {
        favoriteMovieDB.favoriteMovieDao().insertMovie(
            convertModelToEntity(movie)
        )
    }

    override suspend fun deleteMovie(id: Int) {
        favoriteMovieDB.favoriteMovieDao().deleteMovieById(id)
    }

    override suspend fun isFavoriteMovie(id: Int): Boolean {
        return favoriteMovieDB.favoriteMovieDao().isFavoriteMovie(id)
    }
}