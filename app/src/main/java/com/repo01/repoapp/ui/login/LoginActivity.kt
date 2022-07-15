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
import com.repo01.repoapp.ui.main.MainActivity
import com.repo01.repoapp.util.Auth
import com.repo01.repoapp.util.PrintLog
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

        binding.btnLogin.setOnClickListener {
            login()
        }

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
        viewModel.token.observe(this) {
            if (it.isNullOrEmpty()) {
                Toast.makeText(this, R.string.login_fail_message, Toast.LENGTH_SHORT).show()
            } else {
                interceptor.setToken(it)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val uri = intent.data
        PrintLog.printLog(uri.toString())
        if (uri != null && uri.toString().startsWith(Auth.REDIRECT_URI)) {
            uri.getQueryParameter("code")?.let {
                PrintLog.printLog(it.toString())
                viewModel.getAccessToken(it)
            }
        }
    }
}