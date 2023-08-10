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
import com.beside153.peopleinside.view.common.BottomSheetListAdapter.BottomSheetModel.FixDeleteItem
import com.beside153.peopleinside.view.common.BottomSheetListAdapter.BottomSheetModel.ReportItem

class BottomSheetListAdapter(
    private val onReportItemClick: (item: ReportModel) -> Unit,
    private val onFixDeleteItemClick: (item: String) -> Unit
) :
    ListAdapter<BottomSheetModel, BottomSheetListAdapter.ViewHolder>(BottomSheetItemDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ReportItem -> BottomSheetType.ContentReport.ordinal
            is FixDeleteItem -> BottomSheetType.PostFixDelete.ordinal
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

            else -> {
                val binding = ItemBottomSheetBinding.inflate(inflater, parent, false)
                val viewHolder = ViewHolder.FixDeleteItemViewHolder(binding)
                viewHolder.itemView.setOnClickListener {
                    val item = getItem(viewHolder.adapterPosition) as? FixDeleteItem
                    item?.let {
                        onFixDeleteItemClick(it.fixDeleteItem)
                    }
                }
                viewHolder
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder.ReportItemViewHolder -> holder.bind(getItem(position) as ReportItem)
            is ViewHolder.FixDeleteItemViewHolder -> holder.bind(getItem(position) as FixDeleteItem)
        }
    }

    sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        class ReportItemViewHolder(private val binding: ItemBottomSheetBinding) : ViewHolder(binding.root) {
            fun bind(item: ReportItem) {
                binding.item = item
            }
        }

        class FixDeleteItemViewHolder(private val binding: ItemBottomSheetBinding) : ViewHolder(binding.root) {
            fun bind(item: FixDeleteItem) {
                binding.item = item
            }
        }
    }

    sealed class BottomSheetModel {
        data class ReportItem(val reportItem: ReportModel) : BottomSheetModel()
        data class FixDeleteItem(val fixDeleteItem: String) : BottomSheetModel()
    }

    private class BottomSheetItemDiffCallback : DiffUtil.ItemCallback<BottomSheetModel>() {
        override fun areItemsTheSame(oldItem: BottomSheetModel, newItem: BottomSheetModel): Boolean {
            return when {
                oldItem is ReportItem && newItem is ReportItem -> oldItem.reportItem.id == newItem.reportItem.id
                oldItem is FixDeleteItem && newItem is FixDeleteItem -> oldItem.fixDeleteItem == newItem.fixDeleteItem
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: BottomSheetModel, newItem: BottomSheetModel): Boolean {
            return oldItem == newItem
        }
    }
}
