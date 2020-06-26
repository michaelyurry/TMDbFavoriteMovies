package com.yurry.tmdbfavoritemovies.view

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yurry.tmdbfavoritemovies.Constant
import com.yurry.tmdbfavoritemovies.R
import com.yurry.tmdbfavoritemovies.model.Movie
import kotlinx.android.synthetic.main.movie_list_card_item.view.*

class MovieListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var movieList: MutableList<Movie?> = ArrayList()
    private lateinit var mClickListener: ItemClickListener
    private var isLoadingShown = false

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Constant.VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_card_item, parent, false)
            ItemViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.progress_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == Constant.VIEW_TYPE_ITEM) {
            val picasso = Picasso.get()
            val movie = getItem(position)!!

            picasso.load(Constant.IMAGE_500_URL + movie.posterPath)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.itemView.movie_image)
            holder.itemView.movie_title.text = movie.title
            holder.itemView.movie_release_date.text = movie.releaseDate
            holder.itemView.movie_overview.text = movie.overview
            holder.itemView.setOnClickListener {v -> mClickListener.onItemClick(v, movie)}
        }
    }

    interface ItemClickListener{
        fun onItemClick(view: View, movie: Movie)
    }

    private fun getItem(index: Int): Movie? {
        return movieList[index]
    }

    fun setData(movies: List<Movie>) {
        movieList.clear()
        movieList.addAll(movies)
        notifyDataSetChanged()
    }

    fun addData(movies: List<Movie>) {
        movieList.addAll(movies)
        notifyDataSetChanged()
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.mClickListener = itemClickListener
    }

    fun addLoadingView() {
        if (!isLoadingShown) {
            Handler().post {
                movieList.add(null)
                notifyItemInserted(movieList.size - 1)
            }
            isLoadingShown = true
        }
    }

    fun removeLoadingView() {
        if (isLoadingShown) {
            if (movieList.size != 0) {
                movieList.removeAt(movieList.size - 1)
                notifyItemRemoved(movieList.size)
            }
            isLoadingShown = false
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (movieList[position] == null) {
            Constant.VIEW_TYPE_LOADING
        } else {
            Constant.VIEW_TYPE_ITEM
        }
    }
}