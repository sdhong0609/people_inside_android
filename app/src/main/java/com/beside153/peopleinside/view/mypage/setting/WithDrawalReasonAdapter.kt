package com.beside153.peopleinside.view.mypage.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.databinding.ItemWithdrawalReasonBinding
import com.beside153.peopleinside.model.withdrawal.WithDrawalReasonModel

class WithDrawalReasonAdapter(private val onRadioButtonClick: (item: WithDrawalReasonModel) -> Unit) :
    ListAdapter<WithDrawalReasonModel, WithDrawalReasonAdapter.ReasonItemViewHolder>(ReasonItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReasonItemViewHolder {
        val binding = ItemWithdrawalReasonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.radioImageView.setOnClickListener {
            if (binding.item != null) {
                onRadioButtonClick(binding.item!!)
            }
        }
        return ReasonItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReasonItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ReasonItemViewHolder(private val binding: ItemWithdrawalReasonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WithDrawalReasonModel) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    private class ReasonItemDiffCallback : DiffUtil.ItemCallback<WithDrawalReasonModel>() {
        override fun areItemsTheSame(oldItem: WithDrawalReasonModel, newItem: WithDrawalReasonModel): Boolean {
            return oldItem.reasonId == newItem.reasonId
        }

        override fun areContentsTheSame(oldItem: WithDrawalReasonModel, newItem: WithDrawalReasonModel): Boolean {
            return oldItem == newItem
        }
    }
}
