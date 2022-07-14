package com.repo01.repoapp.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.repo01.repoapp.R
import com.repo01.repoapp.databinding.ActivityProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        initToolbar()
        observeData()
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun observeData() {
        viewModel.userInfo.observe(this) {
            binding.userInfo = it
            binding.layoutProfileContainer.repoCount = it.repoCount
        }

        viewModel.starredCount.observe(this) {
            binding.layoutProfileContainer.starredCount = it
        }
    }
}