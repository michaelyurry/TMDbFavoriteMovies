package com.yurry.tmdbfavoritemovies.view

import com.yurry.tmdbfavoritemovies.model.MovieDetail

interface MovieDetailView {
    fun setData(movieDetail: MovieDetail)
    fun showErrorToast(msg: String)
}