package com.yurry.tmdbfavoritemovies.presenter

interface MovieReviewPresenter {
    fun getMovieReview(movieId: Int)
    fun getMoreMovieReview(movieId: Int, page: Int)
}