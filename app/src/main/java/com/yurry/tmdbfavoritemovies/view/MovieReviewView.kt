package com.yurry.tmdbfavoritemovies.view

import com.yurry.tmdbfavoritemovies.model.Review

interface MovieReviewView {
    fun hideLoading()
    fun setData(reviewList: List<Review>)
    fun addData(reviewList: List<Review>)
    fun showErrorToast(msg: String)
    fun showEmptyData()
}