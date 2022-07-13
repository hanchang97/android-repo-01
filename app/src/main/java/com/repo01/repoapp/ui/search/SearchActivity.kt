package com.repo01.repoapp.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.repo01.repoapp.R
import com.repo01.repoapp.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val adapter by lazy { SearchItemAdapter() }
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        initSearchEditText()
        initRecyclerView()
        observeData()
    }

    private fun initSearchEditText() {
        with(binding.etSearch) {
            this.setOnFocusChangeListener { _, hasFocus ->
                this.setCompoundDrawablesWithIntrinsicBounds(
                    if (hasFocus.not()) R.drawable.ic_search else 0,
                    0,
                    if (hasFocus) R.drawable.ic_variant10 else 0,
                    0
                )
            }
            var timer = Timer()
            doAfterTextChanged {
                timer.cancel()
                timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        viewModel.searchRepositoriesByQuery(it.toString())
                    }
                }, 500)
            }
        }
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