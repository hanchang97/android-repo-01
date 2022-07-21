package com.repo01.repoapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.repo01.repoapp.R
import com.repo01.repoapp.databinding.ActivityMainBinding
import com.repo01.repoapp.ui.common.UiState
import com.repo01.repoapp.ui.main.adapter.ViewPagerAdapter
import com.repo01.repoapp.ui.profile.ProfileActivity
import com.repo01.repoapp.ui.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tabTitles = arrayListOf("Issue", "Notifications")
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initView()
        setTabLayoutWithViewPager()
        observeData()
    }

    private fun initView() {
        binding.ivSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        binding.ivProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun setTabLayoutWithViewPager() {
        binding.vpTab.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tlSelect, binding.vpTab) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        for (i in tabTitles.indices) {
            val textView =
                LayoutInflater.from(this).inflate(R.layout.tab_main_title, null) as TextView
            binding.tlSelect.getTabAt(i)?.customView = textView
        }
    }

    private fun observeData() {
        viewModel.uiState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {

                }
                is UiState.Success -> {
                    binding.imageUrl = state.data.avatarUrl
                }
                else -> {
                    Toast.makeText(this, "프로필을 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}