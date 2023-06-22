package com.beside153.peopleinside.view.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ItemSearchSearchingTitleBinding
import com.beside153.peopleinside.databinding.ItemSearchSeenContentBinding
import com.beside153.peopleinside.databinding.ItemSearchTrendContentBinding
import com.beside153.peopleinside.databinding.ItemSearchTrendContentListBinding
import com.beside153.peopleinside.model.search.SearchTrendItem
import com.beside153.peopleinside.model.search.SearchingTitleModel
import com.beside153.peopleinside.view.search.SearchScreenAdapter.SearchScreenModel

class SearchScreenAdapter(private val onSearchTrendItemClick: (item: SearchTrendItem) -> Unit) :
    ListAdapter<SearchScreenModel, SearchScreenAdapter.ViewHolder>(SearchScreenModelDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SearchScreenModel.SearchingTitleItem -> R.layout.item_search_searching_title
            is SearchScreenModel.SeenViewItem -> R.layout.item_search_seen_content
            is SearchScreenModel.TrendViewItem -> R.layout.item_search_trend_content
            is SearchScreenModel.TrendContentItem -> R.layout.item_search_trend_content_list
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_search_searching_title -> {
                val binding = ItemSearchSearchingTitleBinding.inflate(inflater, parent, false)
                ViewHolder.SearchingTitleItemViewHolder(binding)
            }

            R.layout.item_search_seen_content -> {
                val binding = ItemSearchSeenContentBinding.inflate(inflater, parent, false)
                ViewHolder.SeenViewHolder(binding)
            }

            R.layout.item_search_trend_content -> {
                val binding = ItemSearchTrendContentBinding.inflate(inflater, parent, false)
                ViewHolder.TrendViewHolder(binding)
            }

            else -> {
                val binding = ItemSearchTrendContentListBinding.inflate(inflater, parent, false)
                val viewHolder = ViewHolder.TrenListViewHolder(binding)
                viewHolder.itemView.setOnClickListener {
                    val position = viewHolder.adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onSearchTrendItemClick(
                            (getItem(position) as SearchScreenModel.TrendContentItem).searchTrendItem
                        )
                    }
                }
                viewHolder
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder.SearchingTitleItemViewHolder -> holder.bind(
                getItem(position) as SearchScreenModel.SearchingTitleItem
            )
            is ViewHolder.SeenViewHolder -> holder.bind()
            is ViewHolder.TrendViewHolder -> holder.bind()
            is ViewHolder.TrenListViewHolder -> holder.bind(getItem(position) as SearchScreenModel.TrendContentItem)
        }
    }

    sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        class SearchingTitleItemViewHolder(private val binding: ItemSearchSearchingTitleBinding) :
            ViewHolder(binding.root) {
            fun bind(item: SearchScreenModel.SearchingTitleItem) {
                binding.item = item.searchingTitleItem
            }
        }

        class SeenViewHolder(binding: ItemSearchSeenContentBinding) : ViewHolder(binding.root) {

            fun bind() {
                // binding 없음
            }
        }

        class TrendViewHolder(binding: ItemSearchTrendContentBinding) : ViewHolder(binding.root) {

            fun bind() {
                // binding 없음
            }
        }

        class TrenListViewHolder(private val binding: ItemSearchTrendContentListBinding) : ViewHolder(binding.root) {
            fun bind(item: SearchScreenModel.TrendContentItem) {
                binding.item = item.searchTrendItem
            }
        }
    }

    sealed class SearchScreenModel {
        data class SearchingTitleItem(val searchingTitleItem: SearchingTitleModel) : SearchScreenModel()
        object SeenViewItem : SearchScreenModel()
        object TrendViewItem : SearchScreenModel()
        data class TrendContentItem(val searchTrendItem: SearchTrendItem) : SearchScreenModel()
    }
}

private class SearchScreenModelDiffCallback : DiffUtil.ItemCallback<SearchScreenModel>() {
    override fun areItemsTheSame(oldItem: SearchScreenModel, newItem: SearchScreenModel): Boolean {
        return when {
            oldItem is SearchScreenModel.SearchingTitleItem && newItem is SearchScreenModel.SearchingTitleItem ->
                oldItem.searchingTitleItem.id == newItem.searchingTitleItem.id

            oldItem is SearchScreenModel.SeenViewItem && newItem is SearchScreenModel.SeenViewItem -> true
            oldItem is SearchScreenModel.TrendViewItem && newItem is SearchScreenModel.TrendViewItem -> true
            oldItem is SearchScreenModel.TrendContentItem && newItem is SearchScreenModel.TrendContentItem ->
                oldItem.searchTrendItem.id == newItem.searchTrendItem.id

            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: SearchScreenModel, newItem: SearchScreenModel): Boolean {
        return oldItem == newItem
    }
}
