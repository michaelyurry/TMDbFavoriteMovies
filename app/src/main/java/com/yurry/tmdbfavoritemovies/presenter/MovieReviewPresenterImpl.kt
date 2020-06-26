package com.yurry.tmdbfavoritemovies.presenter

import com.yurry.tmdbfavoritemovies.Constant
import com.yurry.tmdbfavoritemovies.model.ReviewResponse
import com.yurry.tmdbfavoritemovies.rest.TMDbRestClient
import com.yurry.tmdbfavoritemovies.view.MovieReviewView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieReviewPresenterImpl(val view: MovieReviewView): MovieReviewPresenter {
    override fun getMovieReview(movieId: Int) {
        TMDbRestClient().getService()
            .getMovieReviews(movieId, Constant.API_KEY, 1)
            .enqueue(object : Callback<ReviewResponse> {
                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                    view.showErrorToast("Error: ${t.message}")
                    view.hideLoading()
                }

                override fun onResponse(
                    call: Call<ReviewResponse>,
                    response: Response<ReviewResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()!!.results.isEmpty()) {
                                view.showEmptyData()
                            } else{
                                view.setData(response.body()!!.results)
                            }
                            view.hideLoading()
                        }
                    } else {
                        response.code()
                        view.showErrorToast("Error: ${response.code()}")
                        view.hideLoading()
                    }
                }
            })    }

    override fun getMoreMovieReview(movieId: Int, page: Int) {
        TMDbRestClient().getService()
            .getMovieReviews(movieId, Constant.API_KEY, page)
            .enqueue(object : Callback<ReviewResponse> {
                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                    view.showErrorToast("Error: ${t.message}")
                    view.hideLoading()
                }

                override fun onResponse(
                    call: Call<ReviewResponse>,
                    response: Response<ReviewResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            view.addData(response.body()!!.results)
                            view.hideLoading()
                        }
                    } else {
                        response.code()
                        view.showErrorToast("Error: ${response.code()}")
                        view.hideLoading()
                    }
                }
            })
    }
}