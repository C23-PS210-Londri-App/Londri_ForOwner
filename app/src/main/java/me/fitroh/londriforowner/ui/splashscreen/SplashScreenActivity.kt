package me.fitroh.londriforowner.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import me.fitroh.londriforowner.R
import me.fitroh.londriforowner.databinding.ActivitySplashScreenBinding
import me.fitroh.londriforowner.models.ViewModelFactory
import me.fitroh.londriforowner.ui.HomeActivity
import me.fitroh.londriforowner.ui.home.HomeViewModel
import me.fitroh.londriforowner.ui.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.getSession().observe(this) { user ->
                if (!user.isLogin) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                } else {
                    val token = user.token
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("extra token", token)
                    startActivity(intent)
                    finish()
                }
            }

        }, 3000)
    }
}