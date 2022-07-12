package com.repo01.repoapp.ui.login

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.repo01.repoapp.BuildConfig
import com.repo01.repoapp.R
import com.repo01.repoapp.data.network.TokenInterceptor
import com.repo01.repoapp.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val redirectUri = "repo://github-auth"
    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var interceptor: TokenInterceptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.btnLogin.setOnClickListener {
            login()
        }

        observeData()
    }

    private fun login() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(
                "https://github.com/login/oauth/authorize?client_id=${BuildConfig.GITHUB_CLIENT_ID}" +
                        "&redirect_uri=$redirectUri"
            )
        )
        startActivity(intent)
    }

    private fun observeData() {
        viewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            interceptor.setToken(it)
        }
    }

    override fun onResume() {
        super.onResume()

        val uri = intent.data
        if (uri != null && uri.toString().startsWith(redirectUri)) {
            val code = uri.getQueryParameter("code")
            if (code != null) {
                viewModel.getAccessToken(code)
            }
        }
    }
}