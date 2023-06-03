package com.beside153.peopleinside.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.databinding.ItemMainViewpagerBinding
import com.beside153.peopleinside.model.Top10Item

class Top10ViewPagerAdapter : ListAdapter<Top10Item, Top10ViewPagerAdapter.Top10ItemViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Top10ItemViewHolder {
        val binding = ItemMainViewpagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = Top10ItemViewHolder(binding)
//        viewHolder.itemView.setOnClickListener {
//            val position = viewHolder.adapterPosition
//            if (position != RecyclerView.NO_POSITION) {
//                onRepositoryItemClick(getItem(position))
//            }
//        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: Top10ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class Top10ItemViewHolder(private val binding: ItemMainViewpagerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Top10Item) {
            binding.item = item
        }
    }
}

private class ItemDiffCallback : DiffUtil.ItemCallback<Top10Item>() {
    override fun areItemsTheSame(oldItem: Top10Item, newItem: Top10Item): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Top10Item, newItem: Top10Item): Boolean {
        return oldItem == newItem
    }
}
