package com.beside153.peopleinside.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.databinding.SearchTrendContentItemBinding
import com.beside153.peopleinside.model.search.SearchTrendItem

class SearchTrendListAdapter(private val onSearchTrendItemClick: (item: SearchTrendItem) -> Unit) :
    ListAdapter<SearchTrendItem, SearchTrendListAdapter.SearchTrendItemViewHolder>(SearchTrendItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchTrendItemViewHolder {
        val binding = SearchTrendContentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = SearchTrendItemViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onSearchTrendItemClick(getItem(position))
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: SearchTrendItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SearchTrendItemViewHolder(private val binding: SearchTrendContentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchTrendItem) {
            binding.item = item
        }
    }
}

private class SearchTrendItemDiffCallback : DiffUtil.ItemCallback<SearchTrendItem>() {
    override fun areItemsTheSame(oldItem: SearchTrendItem, newItem: SearchTrendItem): Boolean {
        return oldItem.contentTitle == newItem.contentTitle
    }

    override fun areContentsTheSame(oldItem: SearchTrendItem, newItem: SearchTrendItem): Boolean {
        return oldItem == newItem
    }
}
