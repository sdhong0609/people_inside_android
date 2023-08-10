package com.beside153.peopleinside.view.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.databinding.ItemCommunityPostListBinding
import com.beside153.peopleinside.model.community.post.CommunityPostModel

class CommunityPostListAdapter(private val onPostItemClick: (item: CommunityPostModel) -> Unit) :
    ListAdapter<CommunityPostModel, CommunityPostListAdapter.PostItemViewHolder>(PostItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {
        val binding = ItemCommunityPostListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = PostItemViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onPostItemClick(getItem(position))
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PostItemViewHolder(private val binding: ItemCommunityPostListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CommunityPostModel) {
            binding.item = item
        }
    }

    private class PostItemDiffCallback : DiffUtil.ItemCallback<CommunityPostModel>() {
        override fun areItemsTheSame(oldItem: CommunityPostModel, newItem: CommunityPostModel): Boolean {
            return oldItem.postId == newItem.postId
        }

        override fun areContentsTheSame(oldItem: CommunityPostModel, newItem: CommunityPostModel): Boolean {
            return oldItem == newItem
        }
    }
}
