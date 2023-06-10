package com.beside153.peopleinside.view.recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.databinding.RecommendRankingItemBinding
import com.beside153.peopleinside.model.RankingItem

class RankingRecyclerViewAdapter(private val onRankingItemClick: (item: RankingItem) -> Unit) :
    ListAdapter<RankingItem, RankingRecyclerViewAdapter.RepositoryItemViewHolder>(RankingItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryItemViewHolder {
        val binding = RecommendRankingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    class RepositoryItemViewHolder(private val binding: RecommendRankingItemBinding) :
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
