package com.beside153.peopleinside.view.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.databinding.ItemMypageRatingContentBinding
import com.beside153.peopleinside.model.mycontent.RatedContentModel

class RatingContentsListAdapter(
    private val onRatingChanged: (rating: Float, item: RatedContentModel) -> Unit,
    private val onVerticalDotsClick: (imageView: ImageView, item: RatedContentModel) -> Unit
) :
    ListAdapter<RatedContentModel, RatingContentsListAdapter.ContentItemViewHolder>(RatingContentItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentItemViewHolder {
        val binding = ItemMypageRatingContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContentItemViewHolder(binding, onRatingChanged, onVerticalDotsClick)
    }

    override fun onBindViewHolder(holder: ContentItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ContentItemViewHolder(
        private val binding: ItemMypageRatingContentBinding,
        private val onRatingChanged: (rating: Float, item: RatedContentModel) -> Unit,
        private val onVerticalDotsClick: (imageView: ImageView, item: RatedContentModel) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RatedContentModel) {
            binding.item = item
            binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                if (fromUser) {
                    ratingBar.rating = rating
                    onRatingChanged(ratingBar.rating, item)
                }
            }
            binding.verticalDotsImageView.setOnClickListener {
                onVerticalDotsClick(binding.verticalDotsImageView, item)
            }
            binding.executePendingBindings()
        }
    }
}

private class RatingContentItemDiffCallback : DiffUtil.ItemCallback<RatedContentModel>() {
    override fun areItemsTheSame(oldItem: RatedContentModel, newItem: RatedContentModel): Boolean {
        return oldItem.contentId == newItem.contentId
    }

    override fun areContentsTheSame(oldItem: RatedContentModel, newItem: RatedContentModel): Boolean {
        return oldItem == newItem
    }
}
