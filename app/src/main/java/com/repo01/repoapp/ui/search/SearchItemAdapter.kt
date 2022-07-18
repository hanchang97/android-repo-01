package com.repo01.repoapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.repo01.repoapp.R
import com.repo01.repoapp.data.model.SearchItemModel
import com.repo01.repoapp.databinding.ItemSearchListBinding

class SearchItemAdapter :
    PagingDataAdapter<SearchItemModel, SearchItemAdapter.SearchItemViewHolder>(diffUtil) {

    class SearchItemViewHolder(val binding: ItemSearchListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(searchItem: SearchItemModel) {
            binding.searchItem = searchItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        return SearchItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_search_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SearchItemModel>() {
            override fun areItemsTheSame(
                oldItem: SearchItemModel,
                newItem: SearchItemModel
            ): Boolean {
                return oldItem.repoName == newItem.repoName && oldItem.ownerName == newItem.ownerName
            }

            override fun areContentsTheSame(
                oldItem: SearchItemModel,
                newItem: SearchItemModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}