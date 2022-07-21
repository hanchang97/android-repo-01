package com.repo01.repoapp.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.repo01.repoapp.BuildConfig
import com.repo01.repoapp.R
import com.repo01.repoapp.data.network.TokenInterceptor
import com.repo01.repoapp.databinding.ActivityLoginBinding
import com.repo01.repoapp.ui.common.UiState
import com.repo01.repoapp.ui.main.MainActivity
import com.repo01.repoapp.util.Auth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var interceptor: TokenInterceptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        observeData()
    }

    private fun login() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(Auth.AUTH_BASE_URL).buildUpon()
                .appendQueryParameter(Auth.AUTH_PARAM_CLIENT_ID, BuildConfig.GITHUB_CLIENT_ID)
                .appendQueryParameter(Auth.AUTH_PARAM_REDIRECT_URI, Auth.REDIRECT_URI)
                .appendQueryParameter(Auth.AUTH_PARAM_SCOPE, Auth.SCOPE)
                .build()
        )
        startActivity(intent)
    }

    private fun observeData() {
        viewModel.loginClickEvent.observe(this) {
            login()
        }
        viewModel.uiState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.btnLogin.isEnabled = false
                }
                is UiState.Success -> {
                    binding.progressBar.isGone = true
                    interceptor.setToken(state.data)
                    Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is UiState.Empty -> {
                    binding.progressBar.isGone = true
                    Toast.makeText(this, R.string.login_fail_message, Toast.LENGTH_SHORT).show()
                    binding.btnLogin.isEnabled = true

                }
                is UiState.Error -> {
                    binding.progressBar.isGone = true
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                    binding.btnLogin.isEnabled = true
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val uri = intent.data
        if (uri != null && uri.toString().startsWith(Auth.REDIRECT_URI)) {
            uri.getQueryParameter("code")?.let {
                viewModel.getAccessToken(it)
            }
        }
    }
}