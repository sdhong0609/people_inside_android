package com.beside153.peopleinside.view.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.databinding.ItemNotificationBinding
import com.beside153.peopleinside.model.notification.NotificationItem

class NotificationListAdapter :
    ListAdapter<NotificationItem, NotificationListAdapter.NotificationItemViewHolder>(NotificationItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationItemViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class NotificationItemViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NotificationItem) {
            binding.item = item
        }
    }
}

private class NotificationItemDiffCallback : DiffUtil.ItemCallback<NotificationItem>() {
    override fun areItemsTheSame(oldItem: NotificationItem, newItem: NotificationItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NotificationItem, newItem: NotificationItem): Boolean {
        return oldItem == newItem
    }
}
