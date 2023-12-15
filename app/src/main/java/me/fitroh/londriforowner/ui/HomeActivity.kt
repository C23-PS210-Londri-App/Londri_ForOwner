package me.fitroh.londriforowner.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import me.fitroh.londriforowner.R
import me.fitroh.londriforowner.databinding.ActivityHomeBinding
import me.fitroh.londriforowner.models.ViewModelFactory
import me.fitroh.londriforowner.ui.home.HomeViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var statusLaundryBoolean: Boolean = false

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get Token from splashscreen activity
        val tokenSession = intent.getStringExtra("extra_token")
        var token = "Null"
        if(tokenSession != null){
            token = tokenSession
        }

        binding.swOnOff.visibility = View.GONE

        // Get Status Laundry from API
        getStatusLaundriAPI(token)

        binding.swOnOff.setOnClickListener{
            var status ="Close"
            if(statusLaundryBoolean) {
                status = "Open"
            }
            statusLaundryBoolean = !statusLaundryBoolean

            Log.d("PostStatus", status)
            postStatusToApi(token, status)
        }

        supportActionBar?.hide()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_home)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_order, R.id.navigation_service, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun getStatusLaundriAPI(token: String) {
        viewModel.getStatus(token)

        viewModel.statusLaundryResponse.observe(this) {status ->

            // Jika status Open, maka tulisan tombol yg terlihat adalah Buka
            binding.swOnOff.isChecked = (status != "Open")

            // Set status to variable
            statusLaundryBoolean = (status != "Open")
            Log.d("PostStatusBool", statusLaundryBoolean.toString())
            binding.swOnOff.visibility = View.VISIBLE
        }

    }

    private fun postStatusToApi(token: String, status: String) {
        binding.apply {
            viewModel.updateStatus(token, status)
        }

        viewModel.updateStatusLaundryResponse.observe(this) { response ->
            response?.let {
                if (it.error) {
                    showToast(it.message)
                }
            }
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}