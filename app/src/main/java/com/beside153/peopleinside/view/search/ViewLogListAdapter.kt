package com.beside153.peopleinside.view.search

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.databinding.ItemSearchViewLogBinding
import com.beside153.peopleinside.model.mediacontent.ViewLogContentModel
import com.beside153.peopleinside.util.setOpenActivityAnimation
import com.beside153.peopleinside.view.contentdetail.ContentDetailActivity

class ViewLogListAdapter :
    ListAdapter<ViewLogContentModel, ViewLogListAdapter.ViewLogItemViewHolder>(ViewLogItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewLogItemViewHolder {
        val binding = ItemSearchViewLogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewLogItemViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val intent = ContentDetailActivity.newIntent(parent.context, false, getItem(position).contentId)
                parent.context.startActivity(intent)
                (parent.context as Activity).setOpenActivityAnimation()
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewLogItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewLogItemViewHolder(private val binding: ItemSearchViewLogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ViewLogContentModel) {
            binding.item = item
        }
    }
}

private class ViewLogItemDiffCallback : DiffUtil.ItemCallback<ViewLogContentModel>() {
    override fun areItemsTheSame(oldItem: ViewLogContentModel, newItem: ViewLogContentModel): Boolean {
        return oldItem.contentId == newItem.contentId
    }

    override fun areContentsTheSame(oldItem: ViewLogContentModel, newItem: ViewLogContentModel): Boolean {
        return oldItem == newItem
    }
}
