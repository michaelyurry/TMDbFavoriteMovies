package com.yurry.tmdbfavoritemovies

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.yurry.tmdbfavoritemovies.model.MovieDetail
import com.yurry.tmdbfavoritemovies.presenter.MovieDetailPresenter
import com.yurry.tmdbfavoritemovies.presenter.MovieDetailPresenterImpl
import com.yurry.tmdbfavoritemovies.repository.DBHelper
import com.yurry.tmdbfavoritemovies.repository.DBHelperImpl
import com.yurry.tmdbfavoritemovies.repository.FavoriteMovieDB
import com.yurry.tmdbfavoritemovies.view.MovieDetailView
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.coroutines.*

class MovieDetailActivity : AppCompatActivity(), MovieDetailView{
    private val presenter: MovieDetailPresenter = MovieDetailPresenterImpl(this)
    private lateinit var dbHelper: DBHelper
    private var myJob: Job? = null
    private var isLiked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        dbHelper = DBHelperImpl(FavoriteMovieDB.getDB(applicationContext))

        val movieID = intent.getIntExtra(Constant.MOVIE_KEY, 0)
        if(movieID != 0) {
            presenter.getMovieDetail(movieID)
        }
        back_button.setOnClickListener {
            onBackPressed()
        }
    }

    override fun setData(movieDetail: MovieDetail) {
        toolbar_title.text = movieDetail.title
        setLikeButton(movieDetail.id)
        val picasso = Picasso.get()

        picasso.load(Constant.IMAGE_500_URL + movieDetail.backdropPath)
            .placeholder(R.mipmap.ic_launcher)
            .into(movie_backdrop)

        picasso.load(Constant.IMAGE_500_URL + movieDetail.posterPath)
            .placeholder(R.mipmap.ic_launcher)
            .into(movie_poster)

        movie_title.text = movieDetail.title
        movie_release_date.text = movieDetail.releaseDate
        movie_overview.text = movieDetail.overview
        movie_rating.rating = (movieDetail.voteAverage / 2).toFloat()

        like_button.setOnClickListener {
            if (isLiked){
                deleteMovie(movieDetail.id)
            } else {
                insertMovie(movieDetail)
            }
        }

        comment_button.visibility = View.VISIBLE
        comment_button.setOnClickListener {
            val intent = Intent(applicationContext, MovieReviewActivity::class.java)
            intent.putExtra(Constant.MOVIE_KEY, movieDetail.id)
            intent.putExtra(Constant.MOVIE_TITLE_KEY, movieDetail.title)
            startActivity(intent)
        }

    }

    override fun showErrorToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun setLikeButton(id: Int){
        myJob = CoroutineScope(Dispatchers.IO).launch {
                    val result = dbHelper.isFavoriteMovie(id)
                    withContext(Dispatchers.Main) {
                        like_button.visibility = View.VISIBLE
                        if (result){
                            isLiked = true
                            like_button.setImageResource(R.drawable.icn_like_red_16px)
                        } else {
                            isLiked = false
                            like_button.setImageResource(R.drawable.icn_like_gray_16px)
                        }
                    }
                }
    }

    private fun deleteMovie(id: Int) {
        myJob = CoroutineScope(Dispatchers.IO).launch {
                    dbHelper.deleteMovie(id)
                    withContext(Dispatchers.Main){
                        isLiked = false
                        like_button.setImageResource(R.drawable.icn_like_gray_16px)
                    }
                }
    }

    private fun insertMovie(movieDetail: MovieDetail) {
        myJob = CoroutineScope(Dispatchers.IO).launch {
                    dbHelper.insertMovie(movieDetail)
                    withContext(Dispatchers.Main){
                        isLiked = true
                        like_button.setImageResource(R.drawable.icn_like_red_16px)
                    }
                }
    }

    override fun onDestroy() {
        myJob?.cancel()
        super.onDestroy()
    }

}