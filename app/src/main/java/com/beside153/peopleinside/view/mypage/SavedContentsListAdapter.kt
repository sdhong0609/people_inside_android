package com.beside153.peopleinside.view.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.databinding.ItemMypageSavedContentListBinding
import com.beside153.peopleinside.model.mypage.SavedContentModel

class SavedContentsListAdapter(private val onContentItemClick: (item: SavedContentModel) -> Unit) :
    ListAdapter<SavedContentModel, SavedContentsListAdapter.ContentItemViewHolder>(ContentItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentItemViewHolder {
        val binding = ItemMypageSavedContentListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    class ContentItemViewHolder(private val binding: ItemMypageSavedContentListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SavedContentModel) {
            binding.item = item
        }
    }
}

private class ContentItemDiffCallback : DiffUtil.ItemCallback<SavedContentModel>() {
    override fun areItemsTheSame(oldItem: SavedContentModel, newItem: SavedContentModel): Boolean {
        return oldItem.posterUrl == newItem.posterUrl
    }

    override fun areContentsTheSame(oldItem: SavedContentModel, newItem: SavedContentModel): Boolean {
        return oldItem == newItem
    }
}
