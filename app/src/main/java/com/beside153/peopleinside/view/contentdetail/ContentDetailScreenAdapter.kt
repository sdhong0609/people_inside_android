package com.beside153.peopleinside.view.contentdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ItemContentDetailCommentListBinding
import com.beside153.peopleinside.databinding.ItemContentDetailCommentsBinding
import com.beside153.peopleinside.databinding.ItemContentDetailInfoBinding
import com.beside153.peopleinside.databinding.ItemContentDetailPosterBinding
import com.beside153.peopleinside.databinding.ItemContentDetailReviewBinding
import com.beside153.peopleinside.view.contentdetail.ContentDetailScreenAdapter.ContentDetailScreenModel

class ContentDetailScreenAdapter(private val onCreateReviewClick: () -> Unit) :
    ListAdapter<ContentDetailScreenModel, ContentDetailScreenAdapter.ViewHolder>(ContentDetailModelDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ContentDetailScreenModel.PosterView -> R.layout.item_content_detail_poster
            is ContentDetailScreenModel.ReviewView -> R.layout.item_content_detail_review
            is ContentDetailScreenModel.InfoView -> R.layout.item_content_detail_info
            is ContentDetailScreenModel.CommentsView -> R.layout.item_content_detail_comments
            is ContentDetailScreenModel.CommentItem -> R.layout.item_content_detail_comment_list
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_content_detail_poster -> {
                val binding = ItemContentDetailPosterBinding.inflate(inflater, parent, false)
                ViewHolder.PosterViewHolder(binding)
            }

            R.layout.item_content_detail_review -> {
                val binding = ItemContentDetailReviewBinding.inflate(inflater, parent, false)
                binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, _ ->
                    ratingBar.rating = rating
                }
                binding.createReviewImageButton.setOnClickListener {
                    onCreateReviewClick()
                }
                ViewHolder.ReviewViewHolder(binding)
            }

            R.layout.item_content_detail_info -> {
                val binding = ItemContentDetailInfoBinding.inflate(inflater, parent, false)
                binding.contentDescriptionTextView.apply {
                    text = text.toString().replace(" ", "\u00A0")
                }
                ViewHolder.InfoViewHolder(binding)
            }

            R.layout.item_content_detail_comments -> {
                val binding = ItemContentDetailCommentsBinding.inflate(inflater, parent, false)
                ViewHolder.CommentsViewHolder(binding)
            }

            else -> {
                val binding = ItemContentDetailCommentListBinding.inflate(inflater, parent, false)
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
        class PosterViewHolder(binding: ItemContentDetailPosterBinding) : ViewHolder(binding.root) {

            fun bind() {
                // binding 없음
            }
        }

        class ReviewViewHolder(binding: ItemContentDetailReviewBinding) : ViewHolder(binding.root) {

            fun bind() {
                // binding 없음
            }
        }

        class InfoViewHolder(binding: ItemContentDetailInfoBinding) : ViewHolder(binding.root) {

            fun bind() {
                // binding 없음
            }
        }

        class CommentsViewHolder(binding: ItemContentDetailCommentsBinding) : ViewHolder(binding.root) {

            fun bind() {
                // binding 없음
            }
        }

        class CommentItemViewHolder(private val binding: ItemContentDetailCommentListBinding) :
            ViewHolder(binding.root) {
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
