package com.beside153.peopleinside.view.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.databinding.ItemMbtiTagBinding
import com.beside153.peopleinside.model.community.MbtiTagModel

class MbtiTagListAdapter(private val onMbtiTagItemClick: (item: MbtiTagModel) -> Unit) :
    ListAdapter<MbtiTagModel, MbtiTagListAdapter.MbtiTagItemViewHolder>(MbtiTagItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MbtiTagItemViewHolder {
        val binding = ItemMbtiTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = MbtiTagItemViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onMbtiTagItemClick(getItem(position))
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: MbtiTagItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MbtiTagItemViewHolder(private val binding: ItemMbtiTagBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MbtiTagModel) {
            binding.item = item
        }
    }

    private class MbtiTagItemDiffCallback : DiffUtil.ItemCallback<MbtiTagModel>() {
        override fun areItemsTheSame(oldItem: MbtiTagModel, newItem: MbtiTagModel): Boolean {
            return oldItem.mbtiTag == newItem.mbtiTag
        }

        override fun areContentsTheSame(oldItem: MbtiTagModel, newItem: MbtiTagModel): Boolean {
            return oldItem == newItem
        }
    }
}
