package com.beside153.peopleinside.view.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ItemSignUpContentChoiceListBinding
import com.beside153.peopleinside.databinding.ItemSignUpContentChoiceTitleBinding
import com.beside153.peopleinside.model.login.OnBoardingContentModel
import com.beside153.peopleinside.view.login.ContentScreenAdapter.ContentScreenModel

class ContentScreenAdapter(private val onContentItemClick: (item: OnBoardingContentModel) -> Unit) :
    ListAdapter<ContentScreenModel, ContentScreenAdapter.ViewHolder>(ContentScreenModelDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ContentScreenModel.TitleViewItem -> R.layout.item_sign_up_content_choice_title
            is ContentScreenModel.ContentListItem -> R.layout.item_sign_up_content_choice_list
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_sign_up_content_choice_title -> {
                val binding = ItemSignUpContentChoiceTitleBinding.inflate(inflater, parent, false)
                ViewHolder.TitleViewHolder(binding)
            }

            else -> {
                val binding = ItemSignUpContentChoiceListBinding.inflate(inflater, parent, false)
                val viewHolder = ViewHolder.ContentListItemViewHolder(binding)
                viewHolder.itemView.setOnClickListener {
                    val position = viewHolder.adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onContentItemClick((getItem(position) as ContentScreenModel.ContentListItem).contentModel)
                    }
                }
                viewHolder
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder.TitleViewHolder -> holder.bind()
            is ViewHolder.ContentListItemViewHolder -> holder.bind(
                getItem(position) as ContentScreenModel.ContentListItem
            )
        }
    }

    sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        class TitleViewHolder(binding: ItemSignUpContentChoiceTitleBinding) : ViewHolder(binding.root) {
            fun bind() {
                // binding 없음
            }
        }

        class ContentListItemViewHolder(private val binding: ItemSignUpContentChoiceListBinding) :
            ViewHolder(binding.root) {
            fun bind(item: ContentScreenModel.ContentListItem) {
                binding.item = item.contentModel
            }
        }
    }

    sealed class ContentScreenModel {
        object TitleViewItem : ContentScreenModel()
        data class ContentListItem(val contentModel: OnBoardingContentModel) : ContentScreenModel()
    }
}

private class ContentScreenModelDiffCallback : DiffUtil.ItemCallback<ContentScreenModel>() {
    override fun areItemsTheSame(oldItem: ContentScreenModel, newItem: ContentScreenModel): Boolean {
        return when {
            oldItem is ContentScreenModel.TitleViewItem && newItem is ContentScreenModel.TitleViewItem -> true
            oldItem is ContentScreenModel.ContentListItem && newItem is ContentScreenModel.ContentListItem ->
                oldItem.contentModel.contentId == newItem.contentModel.contentId

            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: ContentScreenModel, newItem: ContentScreenModel): Boolean {
        return oldItem == newItem
    }
}
