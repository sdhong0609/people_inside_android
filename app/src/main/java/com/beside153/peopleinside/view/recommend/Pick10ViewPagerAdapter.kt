package com.beside153.peopleinside.view.recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.databinding.ItemRecommendPick10Binding
import com.beside153.peopleinside.model.recommend.Pick10Model

class Pick10ViewPagerAdapter(private val onPick10ItemClick: () -> Unit, private val onTopCommentClick: () -> Unit) :
    ListAdapter<Pick10Model, Pick10ViewPagerAdapter.Pick10ItemViewHolder>(Pick10ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pick10ItemViewHolder {
        val binding = ItemRecommendPick10Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = Pick10ItemViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onPick10ItemClick()
            }
        }
        binding.mainCommentLayout.setOnClickListener {
            onTopCommentClick()
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: Pick10ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class Pick10ItemViewHolder(private val binding: ItemRecommendPick10Binding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Pick10Model) {
            binding.item = item
        }
    }
}

private class Pick10ItemDiffCallback : DiffUtil.ItemCallback<Pick10Model>() {
    override fun areItemsTheSame(oldItem: Pick10Model, newItem: Pick10Model): Boolean {
        return oldItem.contentId == newItem.contentId
    }

    override fun areContentsTheSame(oldItem: Pick10Model, newItem: Pick10Model): Boolean {
        return oldItem == newItem
    }
}
