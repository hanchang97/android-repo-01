package com.repo01.repoapp.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.repo01.repoapp.R
import com.repo01.repoapp.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val adapter by lazy { SearchItemAdapter() }
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        // TODO edittext change event
        viewModel.searchRepositoriesByQuery("android")

        initRecyclerView()
        observeData()
    }

    private fun initRecyclerView() {
        binding.rvSearchResultList.apply {
            adapter = this@SearchActivity.adapter
            layoutManager = LinearLayoutManager(this@SearchActivity)
        }
    }

    private fun observeData() {
        viewModel.searchResult.observe(this) {
            if (it.isNotEmpty()) {
                adapter.submitList(it)
                showSearchResultRecyclerView()
            } else {
                hideSearchResultRecyclerView()
            }
        }
    }

    private fun showSearchResultRecyclerView() = with(binding) {
        tvSearchDefaultTitle.isGone = true
        tvSearchDefaultContent.isGone = true
        rvSearchResultList.isVisible = true
    }

    private fun hideSearchResultRecyclerView() = with(binding) {
        tvSearchDefaultTitle.isVisible = true
        tvSearchDefaultContent.isVisible = true
        rvSearchResultList.isGone = true
    }
}