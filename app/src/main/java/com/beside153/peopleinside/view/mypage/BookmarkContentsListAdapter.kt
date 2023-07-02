package com.beside153.peopleinside.view.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.databinding.ItemMypageBookmarkContentBinding
import com.beside153.peopleinside.model.mypage.BookmarkedContentModel

class BookmarkContentsListAdapter(private val onContentItemClick: (item: BookmarkedContentModel) -> Unit) :
    ListAdapter<BookmarkedContentModel, BookmarkContentsListAdapter.ContentItemViewHolder>(ContentItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentItemViewHolder {
        val binding = ItemMypageBookmarkContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ContentItemViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onContentItemClick(getItem(position))
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ContentItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ContentItemViewHolder(private val binding: ItemMypageBookmarkContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BookmarkedContentModel) {
            binding.item = item
        }
    }
}

private class ContentItemDiffCallback : DiffUtil.ItemCallback<BookmarkedContentModel>() {
    override fun areItemsTheSame(oldItem: BookmarkedContentModel, newItem: BookmarkedContentModel): Boolean {
        return oldItem.contentId == newItem.contentId
    }

    override fun areContentsTheSame(oldItem: BookmarkedContentModel, newItem: BookmarkedContentModel): Boolean {
        return oldItem == newItem
    }
}
