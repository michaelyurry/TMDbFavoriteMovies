package com.yurry.tmdbfavoritemovies.presenter

interface MoviePresenter {
    fun getPopularMovies()
    fun getMorePopularMovies(page: Int)
    fun getTopRatedMovies()
    fun getMoreTopRatedMovies(page: Int)
    fun getNowPlayingMovies()
    fun getMoreNowPlayingMovies(page: Int)
    fun getUpcomingMovies()
    fun getMoreUpcomingMovies(page: Int)
}