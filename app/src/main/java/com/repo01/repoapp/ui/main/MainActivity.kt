package com.repo01.repoapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.repo01.repoapp.R
import com.repo01.repoapp.databinding.ActivityMainBinding
import com.repo01.repoapp.ui.main.adapter.ViewPagerAdapter
import com.repo01.repoapp.ui.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tabTitles = arrayListOf("Issue", "Notifications")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initView()
        setTabLayoutWithViewPager()
    }

    private fun initView() {
        binding.ivSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        binding.ivProfile.setOnClickListener {

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
}