package me.fitroh.londriforowner.ui.register

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import me.fitroh.londriforowner.R
import me.fitroh.londriforowner.databinding.ActivityRegisterThreeBinding
import me.fitroh.londriforowner.ui.HomeActivity

class RegisterThreeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterThreeBinding

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.imageStory.setOnClickListener{
            showOptionImage()
        }

        binding.apply {
            btnDaftar.setOnClickListener {
                val intent = Intent(this@RegisterThreeActivity, HomeActivity::class.java)
                startActivity(intent)
            }
            btnPrev.setOnClickListener {
                val intent = Intent(this@RegisterThreeActivity, RegisterLokasiActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun allPermissionsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("InflateParams")
    private fun showOptionImage() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater

        val view = inflater.inflate(R.layout.dialog_image, null)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()

        val cameraOption = view.findViewById<ImageView>(R.id.cameraOption)
        cameraOption.setOnClickListener{
            if (!allPermissionsGranted()){
                requestPermissionLauncher.launch(REQUIRED_PERMISSION)
            }
            startCamera()
        }

        val galleryOption = view.findViewById<ImageView>(R.id.galleryOption)
        galleryOption.setOnClickListener{
            if (!allPermissionsGranted()){
                requestPermissionLauncher.launch(REQUIRED_PERMISSION)
            }
            startGallery()
        }
    }

    private fun startGallery() {
        Toast.makeText(this, "Start Gallery", Toast.LENGTH_SHORT).show()
    }

    private fun startCamera() {
        Toast.makeText(this, "Start Camera", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}