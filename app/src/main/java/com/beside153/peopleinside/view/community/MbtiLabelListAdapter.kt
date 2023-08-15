package com.beside153.peopleinside.view.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.databinding.ItemMbtiLabelBinding

class MbtiLabelListAdapter :
    ListAdapter<String, MbtiLabelListAdapter.MbtiLabelItemViewHolder>(MbtiLabelItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MbtiLabelItemViewHolder {
        val binding = ItemMbtiLabelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MbtiLabelItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MbtiLabelItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MbtiLabelItemViewHolder(private val binding: ItemMbtiLabelBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.item = item
        }
    }

    private class MbtiLabelItemDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
