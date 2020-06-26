package com.yurry.tmdbfavoritemovies.view

import com.yurry.tmdbfavoritemovies.model.Movie

interface MovieListView {
    fun hideLoading()
    fun setData(movieList: List<Movie>)
    fun addData(movieList: List<Movie>)
    fun showErrorToast(msg: String)
    fun showEmptyData()
}