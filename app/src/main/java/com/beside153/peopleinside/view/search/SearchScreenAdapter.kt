package com.beside153.peopleinside.view.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ItemSearchNoResultBinding
import com.beside153.peopleinside.databinding.ItemSearchNoViewLogBinding
import com.beside153.peopleinside.databinding.ItemSearchSearchedContentBinding
import com.beside153.peopleinside.databinding.ItemSearchSearchingTitleBinding
import com.beside153.peopleinside.databinding.ItemSearchSeenContentBinding
import com.beside153.peopleinside.databinding.ItemSearchTrendContentBinding
import com.beside153.peopleinside.databinding.ItemSearchTrendContentListBinding
import com.beside153.peopleinside.model.search.SearchHotModel
import com.beside153.peopleinside.model.search.SearchedContentModel
import com.beside153.peopleinside.model.search.SearchingTitleModel
import com.beside153.peopleinside.view.search.SearchScreenAdapter.SearchScreenModel
import com.beside153.peopleinside.view.search.SearchScreenAdapter.SearchScreenModel.HotView
import com.beside153.peopleinside.view.search.SearchScreenAdapter.SearchScreenModel.NoResultView
import com.beside153.peopleinside.view.search.SearchScreenAdapter.SearchScreenModel.NoViewLogView
import com.beside153.peopleinside.view.search.SearchScreenAdapter.SearchScreenModel.SearchHotItem
import com.beside153.peopleinside.view.search.SearchScreenAdapter.SearchScreenModel.SearchedContentItem
import com.beside153.peopleinside.view.search.SearchScreenAdapter.SearchScreenModel.SearchingTitleItem
import com.beside153.peopleinside.view.search.SearchScreenAdapter.SearchScreenModel.SeenView
import com.beside153.peopleinside.viewmodel.search.SearchViewModel

class SearchScreenAdapter(
    private val onSearchingTitleItemClick: (item: SearchingTitleModel) -> Unit,
    private val onSearchHotItemClick: (item: SearchHotModel) -> Unit,
    private val searchViewModel: SearchViewModel
) :
    ListAdapter<SearchScreenModel, SearchScreenAdapter.ViewHolder>(SearchScreenModelDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SearchingTitleItem -> R.layout.item_search_searching_title
            is SearchedContentItem -> R.layout.item_search_searched_content
            is NoResultView -> R.layout.item_search_no_result
            is SeenView -> R.layout.item_search_seen_content
            is NoViewLogView -> R.layout.item_search_no_view_log
            is HotView -> R.layout.item_search_trend_content
            is SearchHotItem -> R.layout.item_search_trend_content_list
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_search_searching_title -> {
                val binding = ItemSearchSearchingTitleBinding.inflate(inflater, parent, false)
                val viewHolder = ViewHolder.SearchingTitleItemViewHolder(binding)
                viewHolder.itemView.setOnClickListener {
                    val position = viewHolder.adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onSearchingTitleItemClick(
                            (getItem(position) as SearchingTitleItem).searchingTitleItem
                        )
                    }
                }
                viewHolder
            }

            R.layout.item_search_searched_content -> {
                val binding = ItemSearchSearchedContentBinding.inflate(inflater, parent, false)
                ViewHolder.SearchedContentItemViewHolder(binding)
            }

            R.layout.item_search_no_result -> {
                val binding = ItemSearchNoResultBinding.inflate(inflater, parent, false)
                ViewHolder.NoResultViewHolder(binding)
            }

            R.layout.item_search_seen_content -> {
                val binding = ItemSearchSeenContentBinding.inflate(inflater, parent, false)
                ViewHolder.SeenViewHolder(binding, searchViewModel)
            }

            R.layout.item_search_no_view_log -> {
                val binding = ItemSearchNoViewLogBinding.inflate(inflater, parent, false)
                ViewHolder.NoViewLogViewHolder(binding)
            }

            R.layout.item_search_trend_content -> {
                val binding = ItemSearchTrendContentBinding.inflate(inflater, parent, false)
                ViewHolder.HotViewHolder(binding)
            }

            else -> {
                val binding = ItemSearchTrendContentListBinding.inflate(inflater, parent, false)
                val viewHolder = ViewHolder.SearchHotItemViewHolder(binding)
                viewHolder.itemView.setOnClickListener {
                    val position = viewHolder.adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onSearchHotItemClick(
                            (getItem(position) as SearchHotItem).searchHotItem
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
                getItem(position) as SearchingTitleItem
            )

            is ViewHolder.SearchedContentItemViewHolder -> holder.bind(
                getItem(position) as SearchedContentItem
            )

            is ViewHolder.NoResultViewHolder -> holder.bind()
            is ViewHolder.SeenViewHolder -> holder.bind()
            is ViewHolder.NoViewLogViewHolder -> holder.bind()
            is ViewHolder.HotViewHolder -> holder.bind()
            is ViewHolder.SearchHotItemViewHolder -> holder.bind(getItem(position) as SearchHotItem)
        }
    }

    sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        class SearchingTitleItemViewHolder(private val binding: ItemSearchSearchingTitleBinding) :
            ViewHolder(binding.root) {
            fun bind(item: SearchingTitleItem) {
                binding.item = item.searchingTitleItem
            }
        }

        class SearchedContentItemViewHolder(private val binding: ItemSearchSearchedContentBinding) :
            ViewHolder(binding.root) {
            fun bind(item: SearchedContentItem) {
                binding.item = item.searchedContentItem
            }
        }

        class NoResultViewHolder(binding: ItemSearchNoResultBinding) : ViewHolder(binding.root) {
            fun bind() {
                // binding 없음
            }
        }

        class SeenViewHolder(
            private val binding: ItemSearchSeenContentBinding,
            private val searchViewModel: SearchViewModel
        ) : ViewHolder(binding.root) {
            fun bind() {
                val viewLogListAdapter = ViewLogListAdapter()
                binding.viewLogRecyclerView.adapter = viewLogListAdapter
                viewLogListAdapter.submitList(searchViewModel.viewLogList.value)
            }
        }

        class NoViewLogViewHolder(binding: ItemSearchNoViewLogBinding) : ViewHolder(binding.root) {
            fun bind() {
                // binding 없음
            }
        }

        class HotViewHolder(binding: ItemSearchTrendContentBinding) : ViewHolder(binding.root) {

            fun bind() {
                // binding 없음
            }
        }

        class SearchHotItemViewHolder(private val binding: ItemSearchTrendContentListBinding) :
            ViewHolder(binding.root) {
            fun bind(item: SearchHotItem) {
                binding.item = item.searchHotItem
            }
        }
    }

    sealed class SearchScreenModel {
        data class SearchingTitleItem(val searchingTitleItem: SearchingTitleModel) : SearchScreenModel()
        data class SearchedContentItem(val searchedContentItem: SearchedContentModel) : SearchScreenModel()
        object NoResultView : SearchScreenModel()
        object SeenView : SearchScreenModel()
        object NoViewLogView : SearchScreenModel()
        object HotView : SearchScreenModel()
        data class SearchHotItem(val searchHotItem: SearchHotModel) : SearchScreenModel()
    }
}

@Suppress("CyclomaticComplexMethod")
private class SearchScreenModelDiffCallback : DiffUtil.ItemCallback<SearchScreenModel>() {
    override fun areItemsTheSame(oldItem: SearchScreenModel, newItem: SearchScreenModel): Boolean {
        return when {
            oldItem is SearchingTitleItem && newItem is SearchingTitleItem ->
                oldItem.searchingTitleItem.id == newItem.searchingTitleItem.id

            oldItem is SearchedContentItem && newItem is SearchedContentItem ->
                oldItem.searchedContentItem.contentId == newItem.searchedContentItem.contentId

            oldItem is NoResultView && newItem is NoResultView -> true
            oldItem is SeenView && newItem is SeenView -> true
            oldItem is NoViewLogView && newItem is NoViewLogView -> true

            oldItem is HotView && newItem is HotView -> true
            oldItem is SearchHotItem && newItem is SearchHotItem ->
                oldItem.searchHotItem.id == newItem.searchHotItem.id

            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: SearchScreenModel, newItem: SearchScreenModel): Boolean {
        return oldItem == newItem
    }
}
