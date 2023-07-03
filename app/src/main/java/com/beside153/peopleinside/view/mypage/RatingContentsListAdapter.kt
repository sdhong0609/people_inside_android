package com.beside153.peopleinside.view.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.databinding.ItemMypageRatingContentBinding
import com.beside153.peopleinside.model.mypage.RatingContentModel

class RatingContentsListAdapter(private val onRatingChanged: (rating: Float, item: RatingContentModel) -> Unit) :
    ListAdapter<RatingContentModel, RatingContentsListAdapter.ContentItemViewHolder>(RatingContentItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentItemViewHolder {
        val binding = ItemMypageRatingContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContentItemViewHolder(binding, onRatingChanged)
    }

    override fun onBindViewHolder(holder: ContentItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ContentItemViewHolder(
        private val binding: ItemMypageRatingContentBinding,
        private val onRatingChanged: (rating: Float, item: RatingContentModel) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RatingContentModel) {
            binding.item = item
            binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                if (fromUser) {
                    ratingBar.rating = rating
                    onRatingChanged(ratingBar.rating, item)
                }
            }
            binding.executePendingBindings()
        }
    }
}

private class RatingContentItemDiffCallback : DiffUtil.ItemCallback<RatingContentModel>() {
    override fun areItemsTheSame(oldItem: RatingContentModel, newItem: RatingContentModel): Boolean {
        return oldItem.rating.ratingId == newItem.rating.ratingId
    }

    override fun areContentsTheSame(oldItem: RatingContentModel, newItem: RatingContentModel): Boolean {
        return oldItem == newItem
    }
}
