package com.beside153.peopleinside.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.SearchSeenContentLayoutBinding
import com.beside153.peopleinside.databinding.SearchTrendContentLayoutBinding
import com.beside153.peopleinside.databinding.SearchTrendItemBinding
import com.beside153.peopleinside.model.search.SearchTrendItem
import com.beside153.peopleinside.view.SearchScreenAdapter.SearchScreenModel

class SearchScreenAdapter(private val onSearchTrendItemClick: (item: SearchTrendItem) -> Unit) :
    ListAdapter<SearchScreenModel, SearchScreenAdapter.ViewHolder>(SearchScreenModelDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SearchScreenModel.SeenViewItem -> R.layout.search_seen_content_layout
            is SearchScreenModel.TrendViewItem -> R.layout.search_trend_content_layout
            is SearchScreenModel.TrendListItem -> R.layout.search_trend_item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.search_seen_content_layout -> {
                val binding = SearchSeenContentLayoutBinding.inflate(inflater, parent, false)
                ViewHolder.SeenViewHolder(binding)
            }

            R.layout.search_trend_content_layout -> {
                val binding = SearchTrendContentLayoutBinding.inflate(inflater, parent, false)
                ViewHolder.TrendViewHolder(binding)
            }

            else -> {
                val binding = SearchTrendItemBinding.inflate(inflater, parent, false)
                val viewHolder = ViewHolder.TrenListViewHolder(binding)
                viewHolder.itemView.setOnClickListener {
                    val position = viewHolder.adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onSearchTrendItemClick((getItem(position) as SearchScreenModel.TrendListItem).searchTrendItem)
                    }
                }
                viewHolder
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder.SeenViewHolder -> holder.bind()
            is ViewHolder.TrendViewHolder -> holder.bind()
            is ViewHolder.TrenListViewHolder -> holder.bind(getItem(position) as SearchScreenModel.TrendListItem)
        }
    }

    sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        class SeenViewHolder(private val binding: SearchSeenContentLayoutBinding) : ViewHolder(binding.root) {

            fun bind() {
                // binding 없음
            }
        }

        class TrendViewHolder(private val binding: SearchTrendContentLayoutBinding) : ViewHolder(binding.root) {

            fun bind() {
                // binding 없음
            }
        }

        class TrenListViewHolder(private val binding: SearchTrendItemBinding) : ViewHolder(binding.root) {
            fun bind(item: SearchScreenModel.TrendListItem) {
                binding.item = item.searchTrendItem
            }
        }
    }

    sealed class SearchScreenModel {
        object SeenViewItem : SearchScreenModel()
        object TrendViewItem : SearchScreenModel()
        data class TrendListItem(val searchTrendItem: SearchTrendItem) : SearchScreenModel()
    }
}

private class SearchScreenModelDiffCallback : DiffUtil.ItemCallback<SearchScreenModel>() {
    override fun areItemsTheSame(oldItem: SearchScreenModel, newItem: SearchScreenModel): Boolean {
        return when {
            oldItem is SearchScreenModel.SeenViewItem && newItem is SearchScreenModel.SeenViewItem -> true
            oldItem is SearchScreenModel.TrendViewItem && newItem is SearchScreenModel.TrendViewItem -> true
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: SearchScreenModel, newItem: SearchScreenModel): Boolean {
        return oldItem == newItem
    }
}
