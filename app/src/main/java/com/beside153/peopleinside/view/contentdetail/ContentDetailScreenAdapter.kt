package com.beside153.peopleinside.view.contentdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.App
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ItemContentDetailCommentListBinding
import com.beside153.peopleinside.databinding.ItemContentDetailCommentsBinding
import com.beside153.peopleinside.databinding.ItemContentDetailInfoBinding
import com.beside153.peopleinside.databinding.ItemContentDetailNoCommentBinding
import com.beside153.peopleinside.databinding.ItemContentDetailPosterBinding
import com.beside153.peopleinside.databinding.ItemContentDetailReviewBinding
import com.beside153.peopleinside.model.mediacontent.ContentDetailModel
import com.beside153.peopleinside.model.mediacontent.rating.ContentRatingModel
import com.beside153.peopleinside.model.mediacontent.review.ContentCommentModel
import com.beside153.peopleinside.view.contentdetail.ContentDetailScreenAdapter.ContentDetailScreenModel

class ContentDetailScreenAdapter(
    private val onBookmarkClick: () -> Unit,
    private val onCreateReviewClick: () -> Unit,
    private val goToNonMemberLoginActivity: () -> Unit,
    private val onRatingChanged: (rating: Float) -> Unit,
    private val onVerticalDotsClick: (item: ContentCommentModel) -> Unit,
    private val onCommentLikeClick: (item: ContentCommentModel) -> Unit
) :
    ListAdapter<ContentDetailScreenModel, ContentDetailScreenAdapter.ViewHolder>(
        ContentDetailScreenModelDiffCallback()
    ) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ContentDetailScreenModel.PosterView -> R.layout.item_content_detail_poster
            is ContentDetailScreenModel.ReviewView -> R.layout.item_content_detail_review
            is ContentDetailScreenModel.InfoView -> R.layout.item_content_detail_info
            is ContentDetailScreenModel.CommentsView -> R.layout.item_content_detail_comments
            is ContentDetailScreenModel.ContentCommentItem -> R.layout.item_content_detail_comment_list
            is ContentDetailScreenModel.NoCommentView -> R.layout.item_content_detail_no_comment
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
                binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                    if (!App.prefs.getIsMember() && fromUser) {
                        goToNonMemberLoginActivity()
                        ratingBar.rating = 0f
                    } else {
                        onRatingChanged(rating)
                    }
                }
                binding.bookmarkImageButton.setOnClickListener {
                    onBookmarkClick()
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

            R.layout.item_content_detail_comment_list -> {
                val binding = ItemContentDetailCommentListBinding.inflate(inflater, parent, false)
                val viewHolder = ViewHolder.CommentItemViewHolder(binding)

                binding.verticalDotsImageView.setOnClickListener {
                    val position = viewHolder.adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onVerticalDotsClick(
                            (getItem(position) as ContentDetailScreenModel.ContentCommentItem).contentCommentItem
                        )
                    }
                }

                binding.heartLikeLayout.setOnClickListener {
                    val item = getItem(viewHolder.adapterPosition) as? ContentDetailScreenModel.ContentCommentItem
                    item?.let {
                        onCommentLikeClick(it.contentCommentItem)
                    }
                }

                binding.descriptionTextView.apply {
                    post {
                        val lineCount = lineCount
                        if (lineCount > 0) {
                            if (layout.getEllipsisCount(lineCount - 1) > 0) {
                                binding.seeDetailTextView.isVisible = true

                                binding.seeDetailTextView.setOnClickListener {
                                    maxLines = Int.MAX_VALUE
                                    binding.seeDetailTextView.isVisible = false
                                }
                            }
                        }
                    }
                }

                viewHolder
            }

            else -> {
                val binding = ItemContentDetailNoCommentBinding.inflate(inflater, parent, false)
                ViewHolder.NoCommentViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder.PosterViewHolder -> holder.bind(getItem(position) as ContentDetailScreenModel.PosterView)
            is ViewHolder.ReviewViewHolder -> holder.bind(getItem(position) as ContentDetailScreenModel.ReviewView)
            is ViewHolder.InfoViewHolder -> holder.bind(getItem(position) as ContentDetailScreenModel.InfoView)
            is ViewHolder.CommentsViewHolder -> holder.bind()
            is ViewHolder.CommentItemViewHolder -> holder.bind(
                getItem(position) as ContentDetailScreenModel.ContentCommentItem
            )

            is ViewHolder.NoCommentViewHolder -> holder.bind()
        }
    }

    sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        class PosterViewHolder(private val binding: ItemContentDetailPosterBinding) : ViewHolder(binding.root) {
            fun bind(item: ContentDetailScreenModel.PosterView) {
                binding.item = item.contentDetailItem
            }
        }

        class ReviewViewHolder(private val binding: ItemContentDetailReviewBinding) : ViewHolder(binding.root) {
            fun bind(item: ContentDetailScreenModel.ReviewView) {
                binding.ratingItem = item.contentRatingItem
                binding.bookmarked = item.bookmarked
                binding.hasReview = item.writerHasReview
            }
        }

        class InfoViewHolder(private val binding: ItemContentDetailInfoBinding) : ViewHolder(binding.root) {
            fun bind(item: ContentDetailScreenModel.InfoView) {
                binding.item = item.contentDetailItem
            }
        }

        class CommentsViewHolder(binding: ItemContentDetailCommentsBinding) : ViewHolder(binding.root) {
            fun bind() = Unit
        }

        class CommentItemViewHolder(private val binding: ItemContentDetailCommentListBinding) :
            ViewHolder(binding.root) {
            fun bind(item: ContentDetailScreenModel.ContentCommentItem) {
                binding.item = item.contentCommentItem
                binding.executePendingBindings()
            }
        }

        class NoCommentViewHolder(binding: ItemContentDetailNoCommentBinding) : ViewHolder(binding.root) {
            fun bind() = Unit
        }
    }

    sealed class ContentDetailScreenModel {
        data class PosterView(val contentDetailItem: ContentDetailModel) : ContentDetailScreenModel()
        data class ReviewView(
            val contentRatingItem: ContentRatingModel,
            val bookmarked: Boolean,
            val writerHasReview: Boolean
        ) :
            ContentDetailScreenModel()

        data class InfoView(val contentDetailItem: ContentDetailModel) : ContentDetailScreenModel()
        object CommentsView : ContentDetailScreenModel()
        data class ContentCommentItem(val contentCommentItem: ContentCommentModel) : ContentDetailScreenModel()
        object NoCommentView : ContentDetailScreenModel()
    }
}

private class ContentDetailScreenModelDiffCallback : DiffUtil.ItemCallback<ContentDetailScreenModel>() {
    override fun areItemsTheSame(oldItem: ContentDetailScreenModel, newItem: ContentDetailScreenModel): Boolean {
        return when {
            oldItem is ContentDetailScreenModel.PosterView && newItem is ContentDetailScreenModel.PosterView ->
                oldItem.contentDetailItem.contentId == newItem.contentDetailItem.contentId

            oldItem is ContentDetailScreenModel.ReviewView && newItem is ContentDetailScreenModel.ReviewView ->
                oldItem.contentRatingItem.ratingId == newItem.contentRatingItem.ratingId

            oldItem is ContentDetailScreenModel.InfoView && newItem is ContentDetailScreenModel.InfoView ->
                oldItem.contentDetailItem.contentId == newItem.contentDetailItem.contentId

            oldItem is ContentDetailScreenModel.CommentsView &&
                newItem is ContentDetailScreenModel.CommentsView -> true

            oldItem is ContentDetailScreenModel.ContentCommentItem &&
                newItem is ContentDetailScreenModel.ContentCommentItem ->
                oldItem.contentCommentItem.reviewId == newItem.contentCommentItem.reviewId

            oldItem is ContentDetailScreenModel.NoCommentView &&
                newItem is ContentDetailScreenModel.NoCommentView -> true

            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: ContentDetailScreenModel, newItem: ContentDetailScreenModel): Boolean {
        return oldItem == newItem
    }
}
