package me.fitroh.londriforowner.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import me.fitroh.londriforowner.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        nextAction()
    }

    private fun nextAction() {

        binding.apply {
            btnNext.isEnabled = false

            edEmail.addTextChangedListener { validateInput() }
            edTelephone.addTextChangedListener { validateInput() }
            edPassword.addTextChangedListener { validateInput() }
            edRepeatPassword.addTextChangedListener { validateInput() }

            btnNext.setOnClickListener {
                if (edRepeatPassword.text.toString() == edPassword.text.toString()) {
                    nextStep()
                } else {
                    Toast.makeText(this@RegisterActivity, "Password tidak sama", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun nextStep() {
        val intent = Intent(this@RegisterActivity, RegisterLokasiActivity::class.java)
        startActivity(intent)
    }

    private fun validateInput() {
        binding.apply {
            val isEmailValid = edEmail.isValidInput()
            val isPasswordValid = edPassword.isValidInput()
            val isTelpValid = edTelephone.isValidInput()
            val isRepeatPasswordValid = edRepeatPassword.isValidInput()

            btnNext.isEnabled =
                isEmailValid && isPasswordValid && isTelpValid && isRepeatPasswordValid
        }
    }

    private fun EditText.isValidInput(): Boolean {
        return length() > 0 && error.isNullOrEmpty()
    }
}