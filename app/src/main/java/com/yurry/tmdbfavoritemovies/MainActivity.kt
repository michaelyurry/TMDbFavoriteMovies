package com.yurry.tmdbfavoritemovies

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import com.yurry.tmdbfavoritemovies.model.Movie
import com.yurry.tmdbfavoritemovies.presenter.MoviePresenter
import com.yurry.tmdbfavoritemovies.presenter.MoviePresenterImpl
import com.yurry.tmdbfavoritemovies.repository.DBHelper
import com.yurry.tmdbfavoritemovies.repository.DBHelperImpl
import com.yurry.tmdbfavoritemovies.repository.FavoriteMovieDB
import com.yurry.tmdbfavoritemovies.view.MovieListAdapter
import com.yurry.tmdbfavoritemovies.view.MovieListView
import com.yurry.tmdbfavoritemovies.view.RecyclerViewLoadMoreScroll
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_bottom_sheet.*

class MainActivity : AppCompatActivity(), MovieListView {
    private val presenter: MoviePresenter = MoviePresenterImpl(this)
    private lateinit var adapter: MovieListAdapter
    private lateinit var dbHelper: DBHelper
    private lateinit var scrollListener: RecyclerViewLoadMoreScroll
    private lateinit var state: ListType

    enum class ListType {
        POPULAR, UPCOMING, TOP_RATED, NOW_PLAYING
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHelper = DBHelperImpl(FavoriteMovieDB.getDB(applicationContext))
        setRecyclerView()
        presenter.getPopularMovies()
        toolbar_title.text = getString(R.string.popular)
        state = ListType.POPULAR
        favorite_button.setOnClickListener {
            val intent = Intent(applicationContext, FavoriteMovieActivity::class.java)
            startActivity(intent)
        }

        val bottomSheetBehavior = from(layoutBottomSheet)

        movie_category_button.setOnClickListener {
            changeSheetView(bottomSheetBehavior)
        }

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetCallback() {

            override fun onStateChanged(view: View, state: Int) {
                when (state) {
                    STATE_EXPANDED -> {
                        movie_category_button.visibility = View.GONE
                    }
                    STATE_COLLAPSED -> {
                        movie_category_button.visibility = View.VISIBLE
                    }
                }
            }

            override fun onSlide(view: View, p1: Float) {
            }
        })

        popular_movie.setOnClickListener {
            presenter.getPopularMovies()
            state = ListType.POPULAR
            toolbar_title.text = getString(R.string.popular)
            changeSheetView(bottomSheetBehavior)
        }
        top_rated_movie.setOnClickListener {
            presenter.getTopRatedMovies()
            state = ListType.TOP_RATED
            toolbar_title.text = getString(R.string.top_rated)
            changeSheetView(bottomSheetBehavior)
        }
        upcoming_movie.setOnClickListener {
            presenter.getUpcomingMovies()
            state = ListType.UPCOMING
            toolbar_title.text = getString(R.string.upcoming)
            changeSheetView(bottomSheetBehavior)
        }
        now_playing_movie.setOnClickListener {
            presenter.getNowPlayingMovies()
            state = ListType.NOW_PLAYING
            toolbar_title.text = getString(R.string.now_playing)
            changeSheetView(bottomSheetBehavior)
        }
    }

    override fun onBackPressed() {
        val bottomSheetBehavior = from(layoutBottomSheet)
        if(bottomSheetBehavior.state == STATE_COLLAPSED){
            super.onBackPressed()
        } else {
            bottomSheetBehavior.state = STATE_COLLAPSED
        }
    }

    override fun hideLoading() {
        loading_view.visibility = View.GONE
    }

    override fun setData(movieList: List<Movie>) {
        no_data_view.visibility = View.GONE
        adapter.setData(movieList)
    }

    override fun addData(movieList: List<Movie>) {
        Handler().postDelayed({
            adapter.removeLoadingView()
            adapter.addData(movieList)
            movie_recycler_view.post {
                adapter.notifyDataSetChanged()
            }
            scrollListener.setLoaded()
        }, 3000)
    }

    override fun showErrorToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showEmptyData() {
        no_data_view.visibility = View.VISIBLE
    }

    private fun setRecyclerView() {
        val linearLayoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = MovieListAdapter()
        movie_recycler_view.layoutManager = linearLayoutManager
        movie_recycler_view.adapter = adapter
        movie_recycler_view.setHasFixedSize(true)
        adapter.setItemClickListener(object : MovieListAdapter.ItemClickListener {
            override fun onItemClick(view: View, movie: Movie) {
                val intent = Intent(applicationContext, MovieDetailActivity::class.java)
                intent.putExtra(Constant.MOVIE_KEY, movie.id)
                startActivity(intent)
            }
        })

        scrollListener = RecyclerViewLoadMoreScroll(linearLayoutManager)
        scrollListener.setOnLoadMoreListener(object :
            RecyclerViewLoadMoreScroll.OnLoadMoreListener {
            override fun onLoadMore(
                currentPage: Int,
                totalItemCount: Int,
                recyclerView: RecyclerView
            ) {
                adapter.addLoadingView()
                when (state) {
                    ListType.POPULAR -> presenter.getMorePopularMovies(currentPage)
                    ListType.UPCOMING -> presenter.getMoreUpcomingMovies(currentPage)
                    ListType.TOP_RATED -> presenter.getMoreTopRatedMovies(currentPage)
                    ListType.NOW_PLAYING -> presenter.getMoreNowPlayingMovies(currentPage)
                }
            }
        })
        movie_recycler_view.addOnScrollListener(scrollListener)
    }

    private fun changeSheetView(bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>){
        if (bottomSheetBehavior.state == STATE_COLLAPSED) {
            bottomSheetBehavior.state = STATE_EXPANDED
        } else {
            bottomSheetBehavior.state = STATE_COLLAPSED
        }
    }
}
