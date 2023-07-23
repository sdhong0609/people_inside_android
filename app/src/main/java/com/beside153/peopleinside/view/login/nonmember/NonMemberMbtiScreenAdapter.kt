package com.beside153.peopleinside.view.login.nonmember

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ItemNonMemberMbtiTitleBinding
import com.beside153.peopleinside.databinding.ItemSignUpMbtiListBinding
import com.beside153.peopleinside.model.common.MbtiModel
import com.beside153.peopleinside.view.login.nonmember.NonMemberMbtiScreenAdapter.NonMemberMbtiScreenModel

class NonMemberMbtiScreenAdapter(private val onMbtiItemClick: (item: MbtiModel) -> Unit) :
    ListAdapter<NonMemberMbtiScreenModel, NonMemberMbtiScreenAdapter.ViewHolder>(
        NonMemberMbtiScreenModelDiffCallback()
    ) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is NonMemberMbtiScreenModel.TitleViewItem -> R.layout.item_non_member_mbti_title
            is NonMemberMbtiScreenModel.MbtiListItem -> R.layout.item_sign_up_mbti_list
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_non_member_mbti_title -> {
                val binding = ItemNonMemberMbtiTitleBinding.inflate(inflater, parent, false)
                ViewHolder.TitleViewHolder(binding)
            }

            else -> {
                val binding = ItemSignUpMbtiListBinding.inflate(inflater, parent, false)
                val viewHolder = ViewHolder.MbtiListItemViewHolder(binding)
                viewHolder.itemView.setOnClickListener {
                    val position = viewHolder.adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onMbtiItemClick(
                            (getItem(position) as NonMemberMbtiScreenModel.MbtiListItem).mbtiModel
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
            is ViewHolder.MbtiListItemViewHolder -> holder.bind(
                getItem(position) as NonMemberMbtiScreenModel.MbtiListItem
            )
        }
    }

    sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        class TitleViewHolder(binding: ItemNonMemberMbtiTitleBinding) : ViewHolder(binding.root) {
            fun bind() {
                // binding 없음
            }
        }

        class MbtiListItemViewHolder(private val binding: ItemSignUpMbtiListBinding) : ViewHolder(binding.root) {
            fun bind(item: NonMemberMbtiScreenModel.MbtiListItem) {
                binding.item = item.mbtiModel
            }
        }
    }

    sealed class NonMemberMbtiScreenModel {
        object TitleViewItem : NonMemberMbtiScreenModel()
        data class MbtiListItem(val mbtiModel: MbtiModel) : NonMemberMbtiScreenModel()
    }

    private class NonMemberMbtiScreenModelDiffCallback : DiffUtil.ItemCallback<NonMemberMbtiScreenModel>() {
        override fun areItemsTheSame(oldItem: NonMemberMbtiScreenModel, newItem: NonMemberMbtiScreenModel): Boolean {
            return when {
                oldItem is NonMemberMbtiScreenModel.TitleViewItem &&
                    newItem is NonMemberMbtiScreenModel.TitleViewItem -> true

                oldItem is NonMemberMbtiScreenModel.MbtiListItem && newItem is NonMemberMbtiScreenModel.MbtiListItem ->
                    oldItem.mbtiModel.mbtiText == newItem.mbtiModel.mbtiText

                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: NonMemberMbtiScreenModel, newItem: NonMemberMbtiScreenModel): Boolean {
            return oldItem == newItem
        }
    }
}
