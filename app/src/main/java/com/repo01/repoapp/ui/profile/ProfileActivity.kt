package com.repo01.repoapp.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.repo01.repoapp.R
import com.repo01.repoapp.databinding.ActivityProfileBinding
import com.repo01.repoapp.ui.common.UiState
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
        viewModel.uiState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is UiState.Success -> {
                    binding.progressBar.isGone = true
                    binding.userInfo = state.data
                    binding.layoutProfileContainer.repoCount = state.data.repoCount
                    binding.layoutProfileContainer.starredCount = state.data.starredCount
                }
                else -> {
                    binding.progressBar.isGone = true
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}