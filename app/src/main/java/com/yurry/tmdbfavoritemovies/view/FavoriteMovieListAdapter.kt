package com.yurry.tmdbfavoritemovies.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yurry.tmdbfavoritemovies.Constant
import com.yurry.tmdbfavoritemovies.R
import com.yurry.tmdbfavoritemovies.repository.FavoriteMovie
import kotlinx.android.synthetic.main.movie_list_card_item.view.*

class FavoriteMovieListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var movieList: MutableList<FavoriteMovie> = ArrayList()
    private lateinit var mClickListener: ItemClickListener

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_card_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val picasso = Picasso.get()
        val favoriteMovie = getItem(position)

        picasso.load(Constant.IMAGE_500_URL + favoriteMovie.posterPath)
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.itemView.movie_image)
        holder.itemView.movie_title.text = favoriteMovie.title
        holder.itemView.movie_title.text = favoriteMovie.title
        holder.itemView.movie_release_date.text = favoriteMovie.releaseDate
        holder.itemView.movie_overview.text = favoriteMovie.overview
        holder.itemView.setOnClickListener {v -> mClickListener.onItemClick(v, favoriteMovie)}    }

    interface ItemClickListener{
        fun onItemClick(view: View, favoriteMovie: FavoriteMovie)
    }

    private fun getItem(index: Int): FavoriteMovie {
        return movieList[index]
    }

    fun setData(movieDiscovers: List<FavoriteMovie>) {
        movieList.clear()
        movieList.addAll(movieDiscovers)
        notifyDataSetChanged()
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.mClickListener = itemClickListener
    }
}