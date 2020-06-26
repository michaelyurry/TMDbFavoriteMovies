package com.yurry.tmdbfavoritemovies.presenter

import com.yurry.tmdbfavoritemovies.Constant
import com.yurry.tmdbfavoritemovies.model.MovieDetail
import com.yurry.tmdbfavoritemovies.rest.TMDbRestClient
import com.yurry.tmdbfavoritemovies.view.MovieDetailView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailPresenterImpl(val view: MovieDetailView) : MovieDetailPresenter{

    override fun getMovieDetail(id: Int) {
        TMDbRestClient().getService()
            .getMovieDetail(id, Constant.API_KEY)
            .enqueue(object : Callback<MovieDetail> {
                override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                    view.showErrorToast("Error: ${t.message}")
                }

                override fun onResponse(
                    call: Call<MovieDetail>,
                    response: Response<MovieDetail>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            view.setData(response.body()!!)
                        }
                    } else {
                        response.code()
                        view.showErrorToast("Error: ${response.code()}")
                    }
                }
            })
    }

}