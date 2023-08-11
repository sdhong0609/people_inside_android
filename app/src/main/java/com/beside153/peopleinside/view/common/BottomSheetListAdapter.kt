package com.beside153.peopleinside.view.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.databinding.ItemBottomSheetBinding
import com.beside153.peopleinside.model.report.ReportModel
import com.beside153.peopleinside.view.common.BottomSheetListAdapter.BottomSheetModel
import com.beside153.peopleinside.view.common.BottomSheetListAdapter.BottomSheetModel.CommentFixDeleteItem
import com.beside153.peopleinside.view.common.BottomSheetListAdapter.BottomSheetModel.PostFixDeleteItem
import com.beside153.peopleinside.view.common.BottomSheetListAdapter.BottomSheetModel.ReportItem

class BottomSheetListAdapter(
    private val onReportItemClick: (item: ReportModel) -> Unit,
    private val onPostFixDeleteClick: (item: String) -> Unit,
    private val onCommnetFixDeleteClick: (item: String) -> Unit
) :
    ListAdapter<BottomSheetModel, BottomSheetListAdapter.ViewHolder>(BottomSheetItemDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ReportItem -> BottomSheetType.ContentReport.ordinal
            is PostFixDeleteItem -> BottomSheetType.PostFixDelete.ordinal
            is CommentFixDeleteItem -> BottomSheetType.CommentFixDelete.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            BottomSheetType.ContentReport.ordinal -> {
                val binding = ItemBottomSheetBinding.inflate(inflater, parent, false)
                val viewHolder = ViewHolder.ReportItemViewHolder(binding)
                viewHolder.itemView.setOnClickListener {
                    val item = getItem(viewHolder.adapterPosition) as? ReportItem
                    item?.let {
                        onReportItemClick(it.reportItem)
                    }
                }
                viewHolder
            }

            BottomSheetType.PostFixDelete.ordinal -> {
                val binding = ItemBottomSheetBinding.inflate(inflater, parent, false)
                val viewHolder = ViewHolder.PostFixDeleteItemViewHolder(binding)
                viewHolder.itemView.setOnClickListener {
                    val item = getItem(viewHolder.adapterPosition) as? PostFixDeleteItem
                    item?.let {
                        onPostFixDeleteClick(it.fixDeleteItem)
                    }
                }
                viewHolder
            }

            BottomSheetType.CommentFixDelete.ordinal -> {
                val binding = ItemBottomSheetBinding.inflate(inflater, parent, false)
                val viewHolder = ViewHolder.CommentFixDeleteItemViewHolder(binding)
                viewHolder.itemView.setOnClickListener {
                    val item = getItem(viewHolder.adapterPosition) as? CommentFixDeleteItem
                    item?.let {
                        onCommnetFixDeleteClick(it.fixDeleteItem)
                    }
                }
                viewHolder
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder.ReportItemViewHolder -> holder.bind(getItem(position) as ReportItem)
            is ViewHolder.PostFixDeleteItemViewHolder -> holder.bind(getItem(position) as PostFixDeleteItem)
            is ViewHolder.CommentFixDeleteItemViewHolder -> holder.bind(getItem(position) as CommentFixDeleteItem)
        }
    }

    sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        class ReportItemViewHolder(private val binding: ItemBottomSheetBinding) : ViewHolder(binding.root) {
            fun bind(item: ReportItem) {
                binding.item = item
            }
        }

        class PostFixDeleteItemViewHolder(private val binding: ItemBottomSheetBinding) : ViewHolder(binding.root) {
            fun bind(item: PostFixDeleteItem) {
                binding.item = item
            }
        }

        class CommentFixDeleteItemViewHolder(private val binding: ItemBottomSheetBinding) : ViewHolder(binding.root) {
            fun bind(item: CommentFixDeleteItem) {
                binding.item = item
            }
        }
    }

    sealed class BottomSheetModel {
        data class ReportItem(val reportItem: ReportModel) : BottomSheetModel()
        data class PostFixDeleteItem(val fixDeleteItem: String) : BottomSheetModel()
        data class CommentFixDeleteItem(val fixDeleteItem: String) : BottomSheetModel()
    }

    private class BottomSheetItemDiffCallback : DiffUtil.ItemCallback<BottomSheetModel>() {
        override fun areItemsTheSame(oldItem: BottomSheetModel, newItem: BottomSheetModel): Boolean {
            return when {
                oldItem is ReportItem && newItem is ReportItem -> oldItem.reportItem.id == newItem.reportItem.id
                oldItem is PostFixDeleteItem && newItem is PostFixDeleteItem ->
                    oldItem.fixDeleteItem == newItem.fixDeleteItem

                oldItem is CommentFixDeleteItem && newItem is CommentFixDeleteItem ->
                    oldItem.fixDeleteItem == newItem.fixDeleteItem

                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: BottomSheetModel, newItem: BottomSheetModel): Boolean {
            return oldItem == newItem
        }
    }
}
