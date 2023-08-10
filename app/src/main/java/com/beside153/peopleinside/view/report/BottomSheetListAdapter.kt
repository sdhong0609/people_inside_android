package com.beside153.peopleinside.view.report

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ItemReportBinding
import com.beside153.peopleinside.model.report.ReportModel
import com.beside153.peopleinside.view.report.BottomSheetListAdapter.BottomSheetModel
import com.beside153.peopleinside.view.report.BottomSheetListAdapter.BottomSheetModel.ReportItem

class BottomSheetListAdapter(private val onReportItemClick: ((item: ReportModel) -> Unit)? = null) :
    ListAdapter<BottomSheetModel, BottomSheetListAdapter.ViewHolder>(BottomSheetItemDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ReportItem -> R.layout.item_report
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_report -> {
                val binding = ItemReportBinding.inflate(inflater, parent, false)
                val viewHolder = ViewHolder.ReportItemViewHolder(binding)
                viewHolder.itemView.setOnClickListener {
                    val position = viewHolder.adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position) as ReportItem
                        onReportItemClick?.invoke(item.reportItem)
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
        }
    }

    sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        class ReportItemViewHolder(private val binding: ItemReportBinding) : ViewHolder(binding.root) {
            fun bind(item: ReportItem) {
                binding.item = item.reportItem
            }
        }
    }

    sealed class BottomSheetModel {
        data class ReportItem(val reportItem: ReportModel) : BottomSheetModel()
    }

    private class BottomSheetItemDiffCallback : DiffUtil.ItemCallback<BottomSheetModel>() {
        override fun areItemsTheSame(oldItem: BottomSheetModel, newItem: BottomSheetModel): Boolean {
            return when {
                oldItem is ReportItem && newItem is ReportItem -> oldItem.reportItem.id == newItem.reportItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: BottomSheetModel, newItem: BottomSheetModel): Boolean {
            return oldItem == newItem
        }
    }
}
