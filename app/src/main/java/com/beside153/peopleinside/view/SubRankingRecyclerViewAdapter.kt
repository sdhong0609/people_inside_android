package com.beside153.peopleinside.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.databinding.RecommendSubRankingItemBinding
import com.beside153.peopleinside.model.RankingItem

class SubRankingRecyclerViewAdapter(private val onRankingItemClick: (item: RankingItem) -> Unit) :
    ListAdapter<RankingItem, SubRankingRecyclerViewAdapter.RepositoryItemViewHolder>(RankingItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryItemViewHolder {
        val binding = RecommendSubRankingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = RepositoryItemViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onRankingItemClick(getItem(position))
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RepositoryItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RepositoryItemViewHolder(private val binding: RecommendSubRankingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RankingItem) {
            binding.item = item
        }
    }
}

private class RankingItemDiffCallback : DiffUtil.ItemCallback<RankingItem>() {
    override fun areItemsTheSame(oldItem: RankingItem, newItem: RankingItem): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: RankingItem, newItem: RankingItem): Boolean {
        return oldItem == newItem
    }
}
