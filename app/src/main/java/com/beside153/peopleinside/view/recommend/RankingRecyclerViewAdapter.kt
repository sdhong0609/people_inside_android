package com.beside153.peopleinside.view.recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.databinding.ItemRecommendSubRankingBinding
import com.beside153.peopleinside.model.RankingItem

class RankingRecyclerViewAdapter(private val onRankingItemClick: (item: RankingItem) -> Unit) :
    ListAdapter<RankingItem, RankingRecyclerViewAdapter.RankingItemViewHolder>(RankingItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingItemViewHolder {
        val binding = ItemRecommendSubRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = RankingItemViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onRankingItemClick(getItem(position))
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RankingItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RankingItemViewHolder(private val binding: ItemRecommendSubRankingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RankingItem) {
            binding.item = item
        }
    }
}

private class RankingItemDiffCallback : DiffUtil.ItemCallback<RankingItem>() {
    override fun areItemsTheSame(oldItem: RankingItem, newItem: RankingItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RankingItem, newItem: RankingItem): Boolean {
        return oldItem == newItem
    }
}
