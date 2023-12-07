package me.fitroh.londriforowner.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import me.fitroh.londriforowner.databinding.ActivityLoginBinding
import me.fitroh.londriforowner.ui.HomeActivity
import me.fitroh.londriforowner.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        LoginAction()
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
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(intent)
    }
}