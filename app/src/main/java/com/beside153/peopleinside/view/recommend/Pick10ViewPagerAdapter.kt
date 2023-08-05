package com.beside153.peopleinside.view.recommend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ItemRecommendPick10Binding
import com.beside153.peopleinside.databinding.ItemRecommendPick10RefreshBinding
import com.beside153.peopleinside.model.mediacontent.Pick10Model
import com.beside153.peopleinside.view.recommend.Pick10ViewPagerAdapter.Pick10ViewPagerModel

class Pick10ViewPagerAdapter(
    private val onPick10ItemClick: (item: Pick10Model) -> Unit,
    private val onTopReviewClick: (item: Pick10Model) -> Unit,
    private val onBookmarkClick: (item: Pick10Model) -> Unit,
    private val onGoToWriteReviewClick: (item: Pick10Model) -> Unit,
    private val onRefreshClick: () -> Unit
) :
    ListAdapter<Pick10ViewPagerModel, Pick10ViewPagerAdapter.ViewHolder>(Pick10ViewPagerModelDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Pick10ViewPagerModel.Pick10Item -> R.layout.item_recommend_pick10
            is Pick10ViewPagerModel.RefreshView -> R.layout.item_recommend_pick10_refresh
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_recommend_pick10 -> {
                val binding = ItemRecommendPick10Binding.inflate(inflater, parent, false)
                val viewHolder = ViewHolder.Pick10ItemViewHolder(binding)
                viewHolder.itemView.setOnClickListener {
                    val position = viewHolder.adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onPick10ItemClick((getItem(position) as Pick10ViewPagerModel.Pick10Item).pick10Item)
                    }
                }
                binding.topCommentLayout.setOnClickListener {
                    val position = viewHolder.adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onTopReviewClick((getItem(position) as Pick10ViewPagerModel.Pick10Item).pick10Item)
                    }
                }
                binding.bookmarkImageView.setOnClickListener {
                    val position = viewHolder.adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onBookmarkClick((getItem(position) as Pick10ViewPagerModel.Pick10Item).pick10Item)
                    }
                }
                binding.hasNoCommentLayout.setOnClickListener(null)
                binding.goToWriteReviewButton.setOnClickListener {
                    val position = viewHolder.adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onGoToWriteReviewClick((getItem(position) as Pick10ViewPagerModel.Pick10Item).pick10Item)
                    }
                }
                viewHolder
            }

            else -> {
                val binding = ItemRecommendPick10RefreshBinding.inflate(inflater, parent, false)
                binding.refreshLayout.setOnClickListener {
                    onRefreshClick()
                }
                ViewHolder.RefreshViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder.Pick10ItemViewHolder -> holder.bind(getItem(position) as Pick10ViewPagerModel.Pick10Item)
            is ViewHolder.RefreshViewHolder -> holder.bind()
        }
    }

    sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        class Pick10ItemViewHolder(private val binding: ItemRecommendPick10Binding) : ViewHolder(binding.root) {

            fun bind(item: Pick10ViewPagerModel.Pick10Item) {
                binding.item = item.pick10Item
                binding.writer = item.pick10Item.topLikeReview?.writer
                binding.hasReview = item.pick10Item.topLikeReview != null
                binding.executePendingBindings()
            }
        }

        class RefreshViewHolder(binding: ItemRecommendPick10RefreshBinding) : ViewHolder(binding.root) {

            fun bind() = Unit
        }
    }

    sealed class Pick10ViewPagerModel {
        data class Pick10Item(val pick10Item: Pick10Model) : Pick10ViewPagerModel()
        object RefreshView : Pick10ViewPagerModel()
    }
}

private class Pick10ViewPagerModelDiffCallback : DiffUtil.ItemCallback<Pick10ViewPagerModel>() {
    override fun areItemsTheSame(oldItem: Pick10ViewPagerModel, newItem: Pick10ViewPagerModel): Boolean {
        return when {
            oldItem is Pick10ViewPagerModel.Pick10Item && newItem is Pick10ViewPagerModel.Pick10Item ->
                oldItem.pick10Item.contentId == newItem.pick10Item.contentId

            oldItem is Pick10ViewPagerModel.RefreshView && newItem is Pick10ViewPagerModel.RefreshView -> true
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: Pick10ViewPagerModel, newItem: Pick10ViewPagerModel): Boolean {
        return oldItem == newItem
    }
}
