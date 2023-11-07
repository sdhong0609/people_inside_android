package com.beside153.peopleinside.view.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ItemNonMemberMbtiTitleBinding
import com.beside153.peopleinside.databinding.ItemSignUpMbtiListBinding
import com.beside153.peopleinside.databinding.ItemSignUpMbtiTitleBinding
import com.beside153.peopleinside.model.common.MbtiModel
import com.beside153.peopleinside.view.common.MbtiChoiceScreenAdapter.MbtiScreenModel

class MbtiChoiceScreenAdapter(private val onMbtiItemClick: (item: MbtiModel) -> Unit) :
    ListAdapter<MbtiScreenModel, MbtiChoiceScreenAdapter.ViewHolder>(MbtiScreenModelDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MbtiScreenModel.SignUpTitleView -> R.layout.item_sign_up_mbti_title
            is MbtiScreenModel.NonMemberTitleView -> R.layout.item_non_member_mbti_title
            is MbtiScreenModel.MbtiListItem -> R.layout.item_sign_up_mbti_list
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_sign_up_mbti_title -> {
                val binding = ItemSignUpMbtiTitleBinding.inflate(inflater, parent, false)
                ViewHolder.SingUpTitleViewHolder(binding)
            }

            R.layout.item_non_member_mbti_title -> {
                val binding = ItemNonMemberMbtiTitleBinding.inflate(inflater, parent, false)
                ViewHolder.NonMemberTitleViewHolder(binding)
            }

            else -> {
                val binding = ItemSignUpMbtiListBinding.inflate(inflater, parent, false)
                val viewHolder = ViewHolder.MbtiListItemViewHolder(binding)
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
            is ViewHolder.SingUpTitleViewHolder -> holder.bind()
            is ViewHolder.NonMemberTitleViewHolder -> holder.bind()
            is ViewHolder.MbtiListItemViewHolder -> holder.bind(getItem(position) as MbtiScreenModel.MbtiListItem)
        }
    }

    sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        class SingUpTitleViewHolder(binding: ItemSignUpMbtiTitleBinding) : ViewHolder(binding.root) {
            fun bind() = Unit
        }

        class NonMemberTitleViewHolder(binding: ItemNonMemberMbtiTitleBinding) : ViewHolder(binding.root) {
            fun bind() = Unit
        }

        class MbtiListItemViewHolder(private val binding: ItemSignUpMbtiListBinding) : ViewHolder(binding.root) {
            fun bind(item: MbtiScreenModel.MbtiListItem) {
                binding.item = item.mbtiModel
                binding.executePendingBindings()
            }
        }
    }

    sealed class MbtiScreenModel {
        object SignUpTitleView : MbtiScreenModel()
        object NonMemberTitleView : MbtiScreenModel()
        data class MbtiListItem(val mbtiModel: MbtiModel) : MbtiScreenModel()
    }

    private class MbtiScreenModelDiffCallback : DiffUtil.ItemCallback<MbtiScreenModel>() {
        override fun areItemsTheSame(oldItem: MbtiScreenModel, newItem: MbtiScreenModel): Boolean {
            return when {
                oldItem is MbtiScreenModel.SignUpTitleView && newItem is MbtiScreenModel.SignUpTitleView -> true
                oldItem is MbtiScreenModel.NonMemberTitleView && newItem is MbtiScreenModel.NonMemberTitleView -> true
                oldItem is MbtiScreenModel.MbtiListItem && newItem is MbtiScreenModel.MbtiListItem ->
                    oldItem.mbtiModel.mbtiText == newItem.mbtiModel.mbtiText

                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: MbtiScreenModel, newItem: MbtiScreenModel): Boolean {
            return oldItem == newItem
        }
    }
}
