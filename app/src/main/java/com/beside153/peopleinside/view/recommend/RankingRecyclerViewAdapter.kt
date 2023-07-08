package com.beside153.peopleinside.view.recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.databinding.ItemRecommendSubRankingBinding
import com.beside153.peopleinside.model.recommend.SubRankingModel

class RankingRecyclerViewAdapter(private val onSubRankingItemClick: (item: SubRankingModel) -> Unit) :
    ListAdapter<SubRankingModel, RankingRecyclerViewAdapter.RankingItemViewHolder>(RankingItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingItemViewHolder {
        val binding = ItemRecommendSubRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = RankingItemViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onSubRankingItemClick(getItem(position))
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RankingItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RankingItemViewHolder(private val binding: ItemRecommendSubRankingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SubRankingModel) {
            binding.item = item
        }
    }
}

private class RankingItemDiffCallback : DiffUtil.ItemCallback<SubRankingModel>() {
    override fun areItemsTheSame(oldItem: SubRankingModel, newItem: SubRankingModel): Boolean {
        return oldItem.contentId == newItem.contentId
    }

    override fun areContentsTheSame(oldItem: SubRankingModel, newItem: SubRankingModel): Boolean {
        return oldItem == newItem
    }
}
