package me.fitroh.londriforowner.ui.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import me.fitroh.londriforowner.databinding.ActivityLoginBinding
import me.fitroh.londriforowner.models.UserModel
import me.fitroh.londriforowner.models.ViewModelFactory
import me.fitroh.londriforowner.ui.HomeActivity
import me.fitroh.londriforowner.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    companion object {
        const val KEY = "Bearer "
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        LoginAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun LoginAction() {
        binding.apply {
            btnLogin.isEnabled = false

            tvRegister.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
            edEmailLogin.addTextChangedListener {
                validateInput()
            }

            edPasswordLogin.addTextChangedListener {
                validateInput()
            }

            btnLogin.setOnClickListener {
                loginAccount()
            }
        }
    }

    private fun validateInput() {
        binding.apply {
            val isEmailValid = edEmailLogin.length() > 0 && edEmailLogin.error.isNullOrEmpty()
            val isPasswordValid =
                edPasswordLogin.length() > 0 && edPasswordLogin.error.isNullOrEmpty()

            btnLogin.isEnabled = isEmailValid && isPasswordValid
        }
    }

    private fun loginAccount() {
        viewModel.isLoading.observe(this) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        binding.apply {
            viewModel.postLogin(
                edEmailLogin.text.toString(),
                edPasswordLogin.text.toString()
            )
        }

        viewModel.logResponse.observe(this) { response ->
            response?.let {
                if (it.error!!) {
                    showToast(it.message)
                }
            }
        }

        viewModel.logResponse.observe(this@LoginActivity) { response ->
            saveSession(
                UserModel(
                    KEY + response.response?.token.toString(),
                    true
                )
            )
        }
        viewModel.login()
        viewModel.logResponse.observe(this@LoginActivity) { response ->
            if (response.error == false) {
                val token = response.response?.token.toString()
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                intent.putExtra("extra_token", token)
                startActivity(intent)
                finish()
            } else {
                showToast(response.message.toString())
            }
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun saveSession(session: UserModel) {
        viewModel.saveSession(session)
    }
}