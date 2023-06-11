package com.beside153.peopleinside.view.contentdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ContentDetailCommentsItemBinding
import com.beside153.peopleinside.databinding.ContentDetailCommentsLayoutBinding
import com.beside153.peopleinside.databinding.ContentDetailInfoLayoutBinding
import com.beside153.peopleinside.databinding.ContentDetailPosterLayoutBinding
import com.beside153.peopleinside.databinding.ContentDetailReviewLayoutBinding
import com.beside153.peopleinside.view.contentdetail.ContentDetailScreenAdapter.ContentDetailScreenModel

class ContentDetailScreenAdapter(private val onCreateReviewClick: () -> Unit) :
    ListAdapter<ContentDetailScreenModel, ContentDetailScreenAdapter.ViewHolder>(ContentDetailModelDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ContentDetailScreenModel.PosterView -> R.layout.content_detail_poster_layout
            is ContentDetailScreenModel.ReviewView -> R.layout.content_detail_review_layout
            is ContentDetailScreenModel.InfoView -> R.layout.content_detail_info_layout
            is ContentDetailScreenModel.CommentsView -> R.layout.content_detail_comments_layout
            is ContentDetailScreenModel.CommentItem -> R.layout.content_detail_comments_item
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
                binding.createReviewImageButton.setOnClickListener {
                    onCreateReviewClick()
                }
                ViewHolder.ReviewViewHolder(binding)
            }

            R.layout.content_detail_info_layout -> {
                val binding = ContentDetailInfoLayoutBinding.inflate(inflater, parent, false)
                binding.contentDescriptionTextView.apply {
                    text = text.toString().replace(" ", "\u00A0")
                }
                ViewHolder.InfoViewHolder(binding)
            }

            R.layout.content_detail_comments_layout -> {
                val binding = ContentDetailCommentsLayoutBinding.inflate(inflater, parent, false)
                ViewHolder.CommentsViewHolder(binding)
            }

            else -> {
                val binding = ContentDetailCommentsItemBinding.inflate(inflater, parent, false)
                ViewHolder.CommentItemViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder.PosterViewHolder -> holder.bind()
            is ViewHolder.ReviewViewHolder -> holder.bind()
            is ViewHolder.InfoViewHolder -> holder.bind()
            is ViewHolder.CommentsViewHolder -> holder.bind()
            is ViewHolder.CommentItemViewHolder -> holder.bind(
                getItem(position) as ContentDetailScreenModel.CommentItem
            )
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

        class CommentsViewHolder(binding: ContentDetailCommentsLayoutBinding) : ViewHolder(binding.root) {

            fun bind() {
                // binding 없음
            }
        }

        class CommentItemViewHolder(private val binding: ContentDetailCommentsItemBinding) : ViewHolder(binding.root) {
            fun bind(item: ContentDetailScreenModel.CommentItem) {
                binding.commentItem = item
            }
        }
    }

    sealed class ContentDetailScreenModel {
        object PosterView : ContentDetailScreenModel()
        object ReviewView : ContentDetailScreenModel()
        object InfoView : ContentDetailScreenModel()
        object CommentsView : ContentDetailScreenModel()
        data class CommentItem(val id: Int, val nickname: String, val comment: String) : ContentDetailScreenModel()
    }
}

private class ContentDetailModelDiffCallback : DiffUtil.ItemCallback<ContentDetailScreenModel>() {
    override fun areItemsTheSame(oldItem: ContentDetailScreenModel, newItem: ContentDetailScreenModel): Boolean {
        return when {
            oldItem is ContentDetailScreenModel.PosterView &&
                newItem is ContentDetailScreenModel.PosterView -> true

            oldItem is ContentDetailScreenModel.ReviewView &&
                newItem is ContentDetailScreenModel.ReviewView -> true

            oldItem is ContentDetailScreenModel.InfoView &&
                newItem is ContentDetailScreenModel.InfoView -> true

            oldItem is ContentDetailScreenModel.CommentsView &&
                newItem is ContentDetailScreenModel.CommentsView -> true

            oldItem is ContentDetailScreenModel.CommentItem && newItem is ContentDetailScreenModel.CommentItem ->
                oldItem.id == newItem.id

            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: ContentDetailScreenModel, newItem: ContentDetailScreenModel): Boolean {
        return oldItem == newItem
    }
}
