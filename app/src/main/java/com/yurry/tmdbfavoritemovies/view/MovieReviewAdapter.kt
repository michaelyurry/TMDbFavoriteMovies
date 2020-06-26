package com.yurry.tmdbfavoritemovies.view

import android.content.res.Resources
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yurry.tmdbfavoritemovies.Constant
import com.yurry.tmdbfavoritemovies.R
import com.yurry.tmdbfavoritemovies.model.Review
import kotlinx.android.synthetic.main.movie_review_item.view.*

class MovieReviewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>()  {
    private var reviewList: MutableList<Review?> = ArrayList()
    private lateinit var resource: Resources
    private var isLoadingShown = false

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        resource = parent.context.resources

        return if (viewType == Constant.VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_review_item, parent, false)
            ItemViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.progress_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == Constant.VIEW_TYPE_ITEM) {

            val review: Review = getItem(position)!!
            holder.itemView.review_by.text =
                (resource.getString(R.string.written_by) + review.author)
            holder.itemView.review_content.text = review.content
        }
    }

    private fun getItem(index: Int): Review? {
        return reviewList[index]
    }

    fun setData(reviews: List<Review>) {
        reviewList.clear()
        reviewList.addAll(reviews)
        notifyDataSetChanged()
    }

    fun addData(reviews: List<Review>) {
        reviewList.addAll(reviews)
        notifyDataSetChanged()
    }

    fun addLoadingView() {
        if (!isLoadingShown) {
            Handler().post {
                reviewList.add(null)
                notifyItemInserted(reviewList.size - 1)
            }
            isLoadingShown = true
        }
    }

    fun removeLoadingView() {
        if (isLoadingShown) {
            if (reviewList.size != 0) {
                reviewList.removeAt(reviewList.size - 1)
                notifyItemRemoved(reviewList.size)
            }
            isLoadingShown = false
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (reviewList[position] == null) {
            Constant.VIEW_TYPE_LOADING
        } else {
            Constant.VIEW_TYPE_ITEM
        }
    }
}