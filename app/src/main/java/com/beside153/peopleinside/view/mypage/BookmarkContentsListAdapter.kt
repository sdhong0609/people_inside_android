package com.beside153.peopleinside.view.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.databinding.ItemMypageBookmarkContentBinding
import com.beside153.peopleinside.model.mypage.BookmarkedContentModel

class BookmarkContentsListAdapter(private val onBookmarkClick: (item: BookmarkedContentModel) -> Unit) :
    ListAdapter<BookmarkedContentModel, BookmarkContentsListAdapter.ContentItemViewHolder>(
        BookmarkContentItemDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentItemViewHolder {
        val binding = ItemMypageBookmarkContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContentItemViewHolder(binding, onBookmarkClick)
    }

    override fun onBindViewHolder(holder: ContentItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ContentItemViewHolder(
        private val binding: ItemMypageBookmarkContentBinding,
        private val onBookmarkClick: (item: BookmarkedContentModel) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BookmarkedContentModel) {
            binding.item = item
            binding.bookmarkImageView.setOnClickListener {
                onBookmarkClick(item)
            }
        }
    }
}

private class BookmarkContentItemDiffCallback : DiffUtil.ItemCallback<BookmarkedContentModel>() {
    override fun areItemsTheSame(oldItem: BookmarkedContentModel, newItem: BookmarkedContentModel): Boolean {
        return oldItem.contentId == newItem.contentId
    }

    override fun areContentsTheSame(oldItem: BookmarkedContentModel, newItem: BookmarkedContentModel): Boolean {
        return oldItem == newItem
    }
}
