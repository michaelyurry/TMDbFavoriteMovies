package com.yurry.tmdbfavoritemovies

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yurry.tmdbfavoritemovies.repository.DBHelper
import com.yurry.tmdbfavoritemovies.repository.DBHelperImpl
import com.yurry.tmdbfavoritemovies.repository.FavoriteMovie
import com.yurry.tmdbfavoritemovies.repository.FavoriteMovieDB
import com.yurry.tmdbfavoritemovies.view.FavoriteMovieListAdapter
import kotlinx.android.synthetic.main.activity_favorite_movie.*
import kotlinx.android.synthetic.main.activity_main.loading_view
import kotlinx.android.synthetic.main.activity_main.movie_recycler_view
import kotlinx.coroutines.*

class FavoriteMovieActivity : AppCompatActivity(){
    private lateinit var adapter: FavoriteMovieListAdapter
    private lateinit var dbHelper: DBHelper
    private var myJob: Job? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_movie)
        dbHelper = DBHelperImpl(FavoriteMovieDB.getDB(applicationContext))
        setRecyclerView()
        back_button.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        getAllMovies()
    }

    private fun setRecyclerView() {
        val linearLayoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = FavoriteMovieListAdapter()
        movie_recycler_view.layoutManager = linearLayoutManager
        movie_recycler_view.adapter = adapter

        adapter.setItemClickListener(object : FavoriteMovieListAdapter.ItemClickListener {
            override fun onItemClick(view: View, favoriteMovie: FavoriteMovie) {
                val intent = Intent(applicationContext, MovieDetailActivity::class.java)
                intent.putExtra(Constant.MOVIE_KEY, favoriteMovie.id)
                startActivity(intent)            }
        })
    }

    private fun getAllMovies() {
        myJob = CoroutineScope(Dispatchers.IO).launch {
                    val result = dbHelper.getFavoriteMovies()

                    withContext(Dispatchers.Main) {
                        if (result.isEmpty()){
                            no_data_view.visibility = View.VISIBLE
                        }
                        adapter.setData(result)
                        loading_view.visibility = View.GONE
                    }
                }
    }

    override fun onDestroy() {
        myJob?.cancel()
        super.onDestroy()
    }
}