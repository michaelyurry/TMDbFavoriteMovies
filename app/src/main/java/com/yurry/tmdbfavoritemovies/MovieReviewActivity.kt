package com.yurry.tmdbfavoritemovies

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yurry.tmdbfavoritemovies.model.Review
import com.yurry.tmdbfavoritemovies.presenter.MovieReviewPresenter
import com.yurry.tmdbfavoritemovies.presenter.MovieReviewPresenterImpl
import com.yurry.tmdbfavoritemovies.view.MovieReviewAdapter
import com.yurry.tmdbfavoritemovies.view.MovieReviewView
import com.yurry.tmdbfavoritemovies.view.RecyclerViewLoadMoreScroll
import kotlinx.android.synthetic.main.activity_movie_review.*

class MovieReviewActivity: AppCompatActivity(), MovieReviewView{
    private val presenter: MovieReviewPresenter = MovieReviewPresenterImpl(this)
    private lateinit var adapter: MovieReviewAdapter
    private lateinit var scrollListener: RecyclerViewLoadMoreScroll

    private var movieId: Int = 0
    private lateinit var movieTitle: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_review)
        setRecyclerView()
        movieId = intent.getIntExtra(Constant.MOVIE_KEY, 0)
        movieTitle = intent.getStringExtra(Constant.MOVIE_TITLE_KEY)!!
        if(movieId != 0){
            presenter.getMovieReview(movieId)
        }
        review_title.text = (movieTitle + getString(R.string.movie_review))

        back_button.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setRecyclerView() {
        val linearLayoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        adapter = MovieReviewAdapter()
        movie_review_recycler_view.layoutManager = linearLayoutManager
        movie_review_recycler_view.adapter = adapter
        movie_review_recycler_view.setHasFixedSize(true)

        scrollListener = RecyclerViewLoadMoreScroll(linearLayoutManager)
        scrollListener.setOnLoadMoreListener(object :
            RecyclerViewLoadMoreScroll.OnLoadMoreListener {
            override fun onLoadMore(
                currentPage: Int,
                totalItemCount: Int,
                recyclerView: RecyclerView
            ) {
                if(movieId != 0){
                    adapter.addLoadingView()
                    presenter.getMoreMovieReview(movieId, currentPage)
                }
            }
        })
        movie_review_recycler_view.addOnScrollListener(scrollListener)
    }

    override fun hideLoading() {
        movie_detail_loading.visibility = View.GONE
    }

    override fun setData(reviewList: List<Review>) {
        no_data_view.visibility = View.GONE
        adapter.setData(reviewList)
    }

    override fun addData(reviewList: List<Review>) {
        Handler().postDelayed({
            adapter.removeLoadingView()
            adapter.addData(reviewList)
            movie_review_recycler_view.post {
                adapter.notifyDataSetChanged()
            }
            scrollListener.setLoaded()
        }, 3000)    }

    override fun showErrorToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showEmptyData() {
        no_data_view.visibility = View.VISIBLE
    }
}