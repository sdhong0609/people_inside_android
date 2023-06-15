package com.beside153.peopleinside.view.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ItemSignUpMbtiListBinding
import com.beside153.peopleinside.databinding.ItemSignUpMbtiTitleBinding
import com.beside153.peopleinside.model.login.MbtiModel
import com.beside153.peopleinside.view.login.MbtiScreenAdapter.MbtiScreenModel

class MbtiScreenAdapter(private val onMbtiItemClick: (item: MbtiModel) -> Unit) :
    ListAdapter<MbtiScreenModel, MbtiScreenAdapter.ViewHolder>(MbtiScreenModelDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MbtiScreenModel.TitleViewItem -> R.layout.item_sign_up_mbti_title
            is MbtiScreenModel.MbtiListItem -> R.layout.item_sign_up_mbti_list
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_sign_up_mbti_title -> {
                val binding = ItemSignUpMbtiTitleBinding.inflate(inflater, parent, false)
                ViewHolder.TitleViewHolder(binding)
            }

            else -> {
                val binding = ItemSignUpMbtiListBinding.inflate(inflater, parent, false)
                val viewHolder = ViewHolder.MbtiItemViewHolder(binding)
                viewHolder.itemView.setOnClickListener {
                    val position = viewHolder.adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onMbtiItemClick(
                            (getItem(position) as MbtiScreenModel.MbtiListItem).mbtiModel
                        )
                    }
                }
                viewHolder
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder.TitleViewHolder -> holder.bind()
            is ViewHolder.MbtiItemViewHolder -> holder.bind(getItem(position) as MbtiScreenModel.MbtiListItem)
        }
    }

    sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        class TitleViewHolder(binding: ItemSignUpMbtiTitleBinding) : ViewHolder(binding.root) {
            fun bind() {
                // binding 없음
            }
        }

        class MbtiItemViewHolder(private val binding: ItemSignUpMbtiListBinding) : ViewHolder(binding.root) {
            fun bind(item: MbtiScreenModel.MbtiListItem) {
                binding.item = item.mbtiModel
            }
        }
    }

    sealed class MbtiScreenModel {
        object TitleViewItem : MbtiScreenModel()
        data class MbtiListItem(val mbtiModel: MbtiModel) : MbtiScreenModel()
    }
}

private class MbtiScreenModelDiffCallback : DiffUtil.ItemCallback<MbtiScreenModel>() {
    override fun areItemsTheSame(oldItem: MbtiScreenModel, newItem: MbtiScreenModel): Boolean {
        return when {
            oldItem is MbtiScreenModel.TitleViewItem && newItem is MbtiScreenModel.TitleViewItem -> true
            oldItem is MbtiScreenModel.MbtiListItem && newItem is MbtiScreenModel.MbtiListItem ->
                oldItem.mbtiModel.mbtiText == newItem.mbtiModel.mbtiText

            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: MbtiScreenModel, newItem: MbtiScreenModel): Boolean {
        return oldItem == newItem
    }
}
