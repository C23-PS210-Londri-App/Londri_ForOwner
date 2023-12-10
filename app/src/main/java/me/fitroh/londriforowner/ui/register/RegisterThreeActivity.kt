package me.fitroh.londriforowner.ui.register

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import me.fitroh.londriforowner.R
import me.fitroh.londriforowner.databinding.ActivityRegisterThreeBinding
import me.fitroh.londriforowner.ui.HomeActivity
import me.fitroh.londriforowner.utils.getImageUri

class RegisterThreeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterThreeBinding
    private var currentImageUri: Uri? = null
    private var token: String? = null

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }

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

    private fun allPermissionGranted() = ContextCompat.checkSelfPermission(
        this, REQUIRED_PERMISSION
    ) == PackageManager.PERMISSION_GRANTED


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.imageStory.setOnClickListener {
            showOptionImage()
        }

        if (!allPermissionGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
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

    @SuppressLint("InflateParams")
    private fun showOptionImage() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater

        val view = inflater.inflate(R.layout.dialog_image, null)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()

        val cameraOption = view.findViewById<ImageView>(R.id.cameraOption)
        cameraOption.setOnClickListener {
            startCamera()
            dialog.dismiss()
        }

        val galleryOption = view.findViewById<ImageView>(R.id.galleryOption)
        galleryOption.setOnClickListener {
            startGallery()
            dialog.dismiss()
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private var launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker :", "Tidak ada gambar yang dipilih")
        }

    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.imageStory.setImageURI(it)
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }
}