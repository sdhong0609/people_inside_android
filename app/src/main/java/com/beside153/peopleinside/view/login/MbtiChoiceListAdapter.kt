package com.beside153.peopleinside.view.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.databinding.ItemSignUpMbtiBinding
import com.beside153.peopleinside.model.login.MbtiModel

class MbtiChoiceListAdapter(private val onMbtiItemClick: (item: MbtiModel) -> Unit) :
    ListAdapter<MbtiModel, MbtiChoiceListAdapter.MbtiItemViewHolder>(RankingItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MbtiItemViewHolder {
        val binding = ItemSignUpMbtiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = MbtiItemViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onMbtiItemClick(getItem(position))
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: MbtiItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MbtiItemViewHolder(private val binding: ItemSignUpMbtiBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MbtiModel) {
            binding.item = item
        }
    }
}

private class RankingItemDiffCallback : DiffUtil.ItemCallback<MbtiModel>() {
    override fun areItemsTheSame(oldItem: MbtiModel, newItem: MbtiModel): Boolean {
        return oldItem.imgId == newItem.imgId
    }

    override fun areContentsTheSame(oldItem: MbtiModel, newItem: MbtiModel): Boolean {
        return oldItem == newItem
    }
}
