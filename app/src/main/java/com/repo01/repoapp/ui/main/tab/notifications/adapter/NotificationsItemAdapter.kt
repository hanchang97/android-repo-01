package com.repo01.repoapp.ui.main.tab.notifications.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.repo01.repoapp.data.model.NotificationsItemModel
import com.repo01.repoapp.databinding.ItemNotificationsListBinding
import com.repo01.repoapp.util.DateCalculator

class NotificationsItemAdapter: ListAdapter<NotificationsItemModel, NotificationsItemAdapter.NotificationsItemViewHolder>(NotificationsDiffUtil) {

    class NotificationsItemViewHolder(val binding: ItemNotificationsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(notificationsItem: NotificationsItemModel) {
            binding.notificationsItemModel = notificationsItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsItemViewHolder {
        val binding =
            ItemNotificationsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationsItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationsItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object NotificationsDiffUtil : DiffUtil.ItemCallback<NotificationsItemModel>() {

        override fun areItemsTheSame(oldItem: NotificationsItemModel, newItem: NotificationsItemModel) =
            oldItem.id == oldItem.id

        override fun areContentsTheSame(oldItem: NotificationsItemModel, newItem: NotificationsItemModel) =
            oldItem == newItem

    }
}