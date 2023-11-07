package com.beside153.peopleinside.view.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.databinding.ItemRecentSearchWordBinding

class RecentSearchWordAdapter :
    ListAdapter<String, RecentSearchWordAdapter.WordItemViewHolder>(WordItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordItemViewHolder {
        val binding = ItemRecentSearchWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class WordItemViewHolder(private val binding: ItemRecentSearchWordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.item = item
        }
    }

    private class WordItemDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
