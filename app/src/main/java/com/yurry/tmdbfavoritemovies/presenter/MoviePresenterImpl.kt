package com.yurry.tmdbfavoritemovies.presenter

import com.yurry.tmdbfavoritemovies.Constant
import com.yurry.tmdbfavoritemovies.model.MovieResponse
import com.yurry.tmdbfavoritemovies.rest.TMDbRestClient
import com.yurry.tmdbfavoritemovies.view.MovieListView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviePresenterImpl(val movieListView: MovieListView) : MoviePresenter{

    override fun getPopularMovies() {
        TMDbRestClient().getService()
            .getPopularMovies(Constant.API_KEY, Constant.TMDB_POPULARITY, 1)
            .enqueue(object : Callback<MovieResponse> {
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    movieListView.showErrorToast("Error: ${t.message}")
                    movieListView.hideLoading()
                }

                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()!!.results.isEmpty()) {
                                movieListView.showEmptyData()
                            } else {
                                movieListView.setData(response.body()!!.results)
                            }
                            movieListView.hideLoading()
                        }
                    } else {
                        response.code()
                        movieListView.showErrorToast("Error: ${response.code()}")
                        movieListView.hideLoading()

                    }
                }
            })
    }

    override fun getMorePopularMovies(page: Int) {
        TMDbRestClient().getService()
            .getPopularMovies(Constant.API_KEY, Constant.TMDB_POPULARITY, page)
            .enqueue(object : Callback<MovieResponse> {
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    movieListView.showErrorToast("Error: ${t.message}")
                    movieListView.hideLoading()
                }

                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            movieListView.addData(response.body()!!.results)
                            movieListView.hideLoading()
                        }
                    } else {
                        response.code()
                        movieListView.showErrorToast("Error: ${response.code()}")
                        movieListView.hideLoading()

                    }
                }
            })
    }

    override fun getTopRatedMovies() {
        TMDbRestClient().getService()
            .getTopRatedMovies(Constant.API_KEY, 1)
            .enqueue(object : Callback<MovieResponse> {
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    movieListView.showErrorToast("Error: ${t.message}")
                    movieListView.hideLoading()
                }

                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()!!.results.isEmpty()) {
                                movieListView.showEmptyData()
                            } else {
                                movieListView.setData(response.body()!!.results)
                            }
                            movieListView.hideLoading()
                        }
                    } else {
                        response.code()
                        movieListView.showErrorToast("Error: ${response.code()}")
                        movieListView.hideLoading()

                    }
                }
            })
    }

    override fun getMoreTopRatedMovies(page: Int) {
        TMDbRestClient().getService()
            .getTopRatedMovies(Constant.API_KEY, page)
            .enqueue(object : Callback<MovieResponse> {
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    movieListView.showErrorToast("Error: ${t.message}")
                    movieListView.hideLoading()
                }

                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            movieListView.addData(response.body()!!.results)
                            movieListView.hideLoading()
                        }
                    } else {
                        response.code()
                        movieListView.showErrorToast("Error: ${response.code()}")
                        movieListView.hideLoading()

                    }
                }
            })
    }

    override fun getNowPlayingMovies() {
        TMDbRestClient().getService()
            .getNowPlayingMovies(Constant.API_KEY, 1)
            .enqueue(object : Callback<MovieResponse> {
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    movieListView.showErrorToast("Error: ${t.message}")
                    movieListView.hideLoading()
                }

                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()!!.results.isEmpty()) {
                                movieListView.showEmptyData()
                            } else {
                                movieListView.setData(response.body()!!.results)
                            }
                            movieListView.hideLoading()
                        }
                    } else {
                        response.code()
                        movieListView.showErrorToast("Error: ${response.code()}")
                        movieListView.hideLoading()

                    }
                }
            })
    }

    override fun getMoreNowPlayingMovies(page: Int) {
        TMDbRestClient().getService()
            .getNowPlayingMovies(Constant.API_KEY, page)
            .enqueue(object : Callback<MovieResponse> {
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    movieListView.showErrorToast("Error: ${t.message}")
                    movieListView.hideLoading()
                }

                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            movieListView.addData(response.body()!!.results)
                            movieListView.hideLoading()
                        }
                    } else {
                        response.code()
                        movieListView.showErrorToast("Error: ${response.code()}")
                        movieListView.hideLoading()

                    }
                }
            })
    }

    override fun getUpcomingMovies() {
        TMDbRestClient().getService()
            .getUpcomingMovies(Constant.API_KEY, 1)
            .enqueue(object : Callback<MovieResponse> {
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    movieListView.showErrorToast("Error: ${t.message}")
                    movieListView.hideLoading()
                }

                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()!!.results.isEmpty()) {
                                movieListView.showEmptyData()
                            } else {
                                movieListView.setData(response.body()!!.results)
                            }
                            movieListView.hideLoading()
                        }
                    } else {
                        response.code()
                        movieListView.showErrorToast("Error: ${response.code()}")
                        movieListView.hideLoading()

                    }
                }
            })
    }

    override fun getMoreUpcomingMovies(page: Int) {
        TMDbRestClient().getService()
            .getUpcomingMovies(Constant.API_KEY, page)
            .enqueue(object : Callback<MovieResponse> {
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    movieListView.showErrorToast("Error: ${t.message}")
                    movieListView.hideLoading()
                }

                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            movieListView.addData(response.body()!!.results)
                            movieListView.hideLoading()
                        }
                    } else {
                        response.code()
                        movieListView.showErrorToast("Error: ${response.code()}")
                        movieListView.hideLoading()

                    }
                }
            })
    }

}