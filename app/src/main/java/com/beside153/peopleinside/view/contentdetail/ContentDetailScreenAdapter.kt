package com.beside153.peopleinside.view.contentdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ContentDetailInfoLayoutBinding
import com.beside153.peopleinside.databinding.ContentDetailPosterLayoutBinding
import com.beside153.peopleinside.databinding.ContentDetailReviewLayoutBinding
import com.beside153.peopleinside.view.contentdetail.ContentDetailScreenAdapter.ContentDetailScreenModel

class ContentDetailScreenAdapter :
    ListAdapter<ContentDetailScreenModel, ContentDetailScreenAdapter.ViewHolder>(ContentDetailModelDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ContentDetailScreenModel.PosterViewItem -> R.layout.content_detail_poster_layout
            is ContentDetailScreenModel.ReviewViewItem -> R.layout.content_detail_review_layout
            is ContentDetailScreenModel.InfoViewItem -> R.layout.content_detail_info_layout
//            is ContentDetailScreenModel.TrendContentItem -> R.layout.search_trend_item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.content_detail_poster_layout -> {
                val binding = ContentDetailPosterLayoutBinding.inflate(inflater, parent, false)
                ViewHolder.PosterViewHolder(binding)
            }

            R.layout.content_detail_review_layout -> {
                val binding = ContentDetailReviewLayoutBinding.inflate(inflater, parent, false)
                binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, _ ->
                    ratingBar.rating = rating
                }
                ViewHolder.ReviewViewHolder(binding)
            }

            else -> {
                val binding = ContentDetailInfoLayoutBinding.inflate(inflater, parent, false)
                ViewHolder.InfoViewHolder(binding)

//                val binding = SearchTrendItemBinding.inflate(inflater, parent, false)
//                val viewHolder = ViewHolder.TrenListViewHolder(binding)
//                viewHolder.itemView.setOnClickListener {
//                    val position = viewHolder.adapterPosition
//                    if (position != RecyclerView.NO_POSITION) {
//                        onSearchTrendItemClick(
//                            (getItem(position) as SearchScreenModel.TrendContentItem).searchTrendItem
//                        )
//                    }
//                }
//                viewHolder
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder.PosterViewHolder -> holder.bind()
            is ViewHolder.ReviewViewHolder -> holder.bind()
            is ViewHolder.InfoViewHolder -> holder.bind()
//            is ViewHolder.TrenListViewHolder -> holder.bind(getItem(position) as SearchScreenModel.TrendContentItem)
        }
    }

    sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        class PosterViewHolder(binding: ContentDetailPosterLayoutBinding) : ViewHolder(binding.root) {

            fun bind() {
                // binding 없음
            }
        }

        class ReviewViewHolder(binding: ContentDetailReviewLayoutBinding) : ViewHolder(binding.root) {

            fun bind() {
                // binding 없음
            }
        }

        class InfoViewHolder(binding: ContentDetailInfoLayoutBinding) : ViewHolder(binding.root) {

            fun bind() {
                // binding 없음
            }
        }

//        class TrenListViewHolder(private val binding: SearchTrendItemBinding) : ViewHolder(binding.root) {
//            fun bind(item: SearchScreenModel.TrendContentItem) {
//                binding.item = item.searchTrendItem
//            }
//        }
    }

    sealed class ContentDetailScreenModel {
        object PosterViewItem : ContentDetailScreenModel()
        object ReviewViewItem : ContentDetailScreenModel()
        object InfoViewItem : ContentDetailScreenModel()
//        data class TrendContentItem(val searchTrendItem: SearchTrendItem) : ContentDetailScreenModel()
    }
}

private class ContentDetailModelDiffCallback : DiffUtil.ItemCallback<ContentDetailScreenModel>() {
    override fun areItemsTheSame(oldItem: ContentDetailScreenModel, newItem: ContentDetailScreenModel): Boolean {
        return when {
            oldItem is ContentDetailScreenModel.PosterViewItem &&
                newItem is ContentDetailScreenModel.PosterViewItem -> true

            oldItem is ContentDetailScreenModel.ReviewViewItem &&
                newItem is ContentDetailScreenModel.ReviewViewItem -> true

            oldItem is ContentDetailScreenModel.InfoViewItem &&
                newItem is ContentDetailScreenModel.InfoViewItem -> true

            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: ContentDetailScreenModel, newItem: ContentDetailScreenModel): Boolean {
        return oldItem == newItem
    }
}
