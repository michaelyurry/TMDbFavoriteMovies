package com.yurry.tmdbfavoritemovies.rest

import com.yurry.tmdbfavoritemovies.model.MovieDetail
import com.yurry.tmdbfavoritemovies.model.MovieResponse
import com.yurry.tmdbfavoritemovies.model.ReviewResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDbApi {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("sort_by") sort: String,
        @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<MovieResponse>


    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<MovieDetail>

    @GET("movie/{movie_id}/reviews")
    fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
        ): Call<ReviewResponse>
}