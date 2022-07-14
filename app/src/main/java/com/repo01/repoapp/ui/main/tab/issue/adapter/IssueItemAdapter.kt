package com.repo01.repoapp.ui.main.tab.issue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.repo01.repoapp.R
import com.repo01.repoapp.data.model.IssueItemModel
import com.repo01.repoapp.databinding.ItemIssueListBinding

class IssueItemAdapter : ListAdapter<IssueItemModel, IssueItemAdapter.IssueItemViewHolder>(IssueDiffUtil) {

    class IssueItemViewHolder(val binding: ItemIssueListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(issueItem: IssueItemModel) {
            binding.issueItemModel = issueItem

            when (issueItem.state) {
                "open" -> binding.ivState.setImageResource(R.drawable.ic_issueopen)
                else -> binding.ivState.setImageResource(R.drawable.ic_issueclosed)
            }
        }
    }
    // 바인딩 어댑터, string resource 사용해서 리팩토링 예정

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueItemViewHolder {
        val binding =
            ItemIssueListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IssueItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IssueItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object IssueDiffUtil : DiffUtil.ItemCallback<IssueItemModel>() {

        override fun areItemsTheSame(oldItem: IssueItemModel, newItem: IssueItemModel) =
            oldItem.id == oldItem.id

        override fun areContentsTheSame(oldItem: IssueItemModel, newItem: IssueItemModel) =
            oldItem == newItem

    }
}