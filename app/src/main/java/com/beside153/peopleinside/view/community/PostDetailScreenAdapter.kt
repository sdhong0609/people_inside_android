package com.beside153.peopleinside.view.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ItemPostDetailBinding
import com.beside153.peopleinside.databinding.ItemPostNoCommentBinding
import com.beside153.peopleinside.model.community.post.CommunityPostModel
import com.beside153.peopleinside.view.community.PostDetailScreenAdapter.PostDetailScreenModel
import com.beside153.peopleinside.view.community.PostDetailScreenAdapter.PostDetailScreenModel.NoCommentView
import com.beside153.peopleinside.view.community.PostDetailScreenAdapter.PostDetailScreenModel.PostItem
import com.beside153.peopleinside.view.community.PostDetailScreenAdapter.ViewHolder

class PostDetailScreenAdapter :
    ListAdapter<PostDetailScreenModel, ViewHolder>(PostDetailScreenModelDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PostItem -> R.layout.item_post_detail
            is NoCommentView -> R.layout.item_post_no_comment
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_post_detail -> {
                val binding = ItemPostDetailBinding.inflate(inflater, parent, false)
                ViewHolder.PostItemViewHolder(binding)
            }

            else -> {
                val binding = ItemPostNoCommentBinding.inflate(inflater, parent, false)
                ViewHolder.NoCommentViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder.PostItemViewHolder -> holder.bind(getItem(position) as PostItem)
            is ViewHolder.NoCommentViewHolder -> holder.bind()
        }
    }

    sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        class PostItemViewHolder(private val binding: ItemPostDetailBinding) : ViewHolder(binding.root) {
            fun bind(item: PostItem) {
                binding.item = item.postItem
            }
        }

        class NoCommentViewHolder(binding: ItemPostNoCommentBinding) : ViewHolder(binding.root) {
            fun bind() = Unit
        }
    }

    sealed class PostDetailScreenModel {
        data class PostItem(val postItem: CommunityPostModel) : PostDetailScreenModel()
        object NoCommentView : PostDetailScreenModel()
    }

    private class PostDetailScreenModelDiffCallback : DiffUtil.ItemCallback<PostDetailScreenModel>() {
        override fun areItemsTheSame(oldItem: PostDetailScreenModel, newItem: PostDetailScreenModel): Boolean {
            return when {
                oldItem is PostItem && newItem is PostItem -> oldItem.postItem.postId == newItem.postItem.postId
                oldItem is NoCommentView && newItem is NoCommentView -> true
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: PostDetailScreenModel, newItem: PostDetailScreenModel): Boolean {
            return oldItem == newItem
        }
    }
}
