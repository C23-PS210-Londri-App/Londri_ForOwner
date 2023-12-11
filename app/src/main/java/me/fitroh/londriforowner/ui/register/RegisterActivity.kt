package me.fitroh.londriforowner.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import me.fitroh.londriforowner.databinding.ActivityRegisterBinding

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var doubleBackToExit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        val keyName = intent.getStringExtra("name")
        val keyEmail = intent.getStringExtra("email")
        val keyTelp = intent.getStringExtra("telephone")
        val keyPassword = intent.getStringExtra("password")
        val keyRepeatPassword = intent.getStringExtra("repeatPass")

        binding.apply {
            edName.setText(keyName)
            edEmail.setText(keyEmail)
            edTelephone.setText(keyTelp)
            edPassword.setText(keyPassword)
            edRepeatPassword.setText(keyRepeatPassword)
            btnNext.isEnabled = true
        }

        nextAction()
    }

    private fun nextAction() {


        binding.apply {
            btnNext.isEnabled = false

            edName.addTextChangedListener { validateInput() }
            edEmail.addTextChangedListener { validateInput() }
            edTelephone.addTextChangedListener { validateInput() }
            edPassword.addTextChangedListener { validateInput() }
            edRepeatPassword.addTextChangedListener { validateInput() }

            btnNext.setOnClickListener {
                showLoading(true)
                if (edRepeatPassword.text.toString() == edPassword.text.toString()) {
                    nextStep(
                        edName.text.toString(),
                        edEmail.text.toString(),
                        edTelephone.text.toString(),
                        edPassword.text.toString(),
                        edRepeatPassword.text.toString()
                    )
                } else {
                    Toast.makeText(this@RegisterActivity, "Password tidak sama", Toast.LENGTH_SHORT)
                        .show()
                    showLoading(false)
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (doubleBackToExit) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExit = true
        Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show()

        android.os.Handler().postDelayed(
            { doubleBackToExit = false },
            2000
        )
    }

    private fun nextStep(
        name: String,
        email: String, telephone: String,
        password: String,
        repeatPassword: String
    ) {
        val intent = Intent(this@RegisterActivity, RegisterLokasiActivity::class.java)
        intent.putExtra("name", name)
        intent.putExtra("email", email)
        intent.putExtra("telephone", telephone)
        intent.putExtra("password", password)
        intent.putExtra("repeatPass", repeatPassword)
        startActivity(intent)
        finish()
    }

    private fun validateInput() {
        binding.apply {
            val isNameValid = edName.isValidInput()
            val isEmailValid = edEmail.isValidInput()
            val isPasswordValid = edPassword.isValidInput()
            val isTelpValid = edTelephone.isValidInput()
            val isRepeatPasswordValid = edRepeatPassword.isValidInput()

            btnNext.isEnabled =
                isNameValid && isEmailValid && isPasswordValid && isTelpValid && isRepeatPasswordValid
        }
    }

    private fun EditText.isValidInput(): Boolean {
        return length() > 0 && error.isNullOrEmpty()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}