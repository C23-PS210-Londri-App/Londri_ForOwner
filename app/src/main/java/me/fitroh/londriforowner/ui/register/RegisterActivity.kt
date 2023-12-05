package me.fitroh.londriforowner.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.fitroh.londriforowner.R
import me.fitroh.londriforowner.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.apply {
            btnNext.setOnClickListener {
                val intent = Intent(this@RegisterActivity, RegisterLokasiActivity::class.java)
                startActivity(intent)
            }
        }
    }
}