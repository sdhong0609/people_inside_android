package com.beside153.peopleinside.view.report

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.databinding.ItemReportBinding
import com.beside153.peopleinside.model.report.ReportModel

class ReportListAdapter(private val onReportItemClick: (item: ReportModel) -> Unit) :
    ListAdapter<ReportModel, ReportListAdapter.ReportItemViewHolder>(ReportItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportItemViewHolder {
        val binding = ItemReportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ReportItemViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onReportItemClick(getItem(position))
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ReportItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ReportItemViewHolder(private val binding: ItemReportBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ReportModel) {
            binding.item = item
        }
    }
}

private class ReportItemDiffCallback : DiffUtil.ItemCallback<ReportModel>() {
    override fun areItemsTheSame(oldItem: ReportModel, newItem: ReportModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ReportModel, newItem: ReportModel): Boolean {
        return oldItem == newItem
    }
}
