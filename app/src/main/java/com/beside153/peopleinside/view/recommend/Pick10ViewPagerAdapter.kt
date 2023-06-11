package com.beside153.peopleinside.view.recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.databinding.ItemRecommendPick10Binding
import com.beside153.peopleinside.model.Pick10Item

class Pick10ViewPagerAdapter :
    ListAdapter<Pick10Item, Pick10ViewPagerAdapter.Pick10ItemViewHolder>(Pick10ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pick10ItemViewHolder {
        val binding = ItemRecommendPick10Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Pick10ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: Pick10ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class Pick10ItemViewHolder(private val binding: ItemRecommendPick10Binding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Pick10Item) {
            binding.item = item
        }
    }
}

private class Pick10ItemDiffCallback : DiffUtil.ItemCallback<Pick10Item>() {
    override fun areItemsTheSame(oldItem: Pick10Item, newItem: Pick10Item): Boolean {
        return oldItem.contentId == newItem.contentId
    }

    override fun areContentsTheSame(oldItem: Pick10Item, newItem: Pick10Item): Boolean {
        return oldItem == newItem
    }
}
