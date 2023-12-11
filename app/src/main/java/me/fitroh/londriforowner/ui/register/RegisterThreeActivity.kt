package me.fitroh.londriforowner.ui.register

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import me.fitroh.londriforowner.R
import me.fitroh.londriforowner.databinding.ActivityRegisterThreeBinding
import me.fitroh.londriforowner.models.ViewModelFactory
import me.fitroh.londriforowner.utils.getImageUri
import me.fitroh.londriforowner.utils.reduceFileImage
import me.fitroh.londriforowner.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class RegisterThreeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterThreeBinding
    private lateinit var keyName: String
    private lateinit var keyEmail: String
    private lateinit var keyTelp: String
    private lateinit var keyPassword: String
    private lateinit var addressBundle: Bundle
    private lateinit var latitude: String
    private lateinit var longitude: String
    private lateinit var address: String
    private var currentImageUri: Uri? = null
    private var selectedImagePart: MultipartBody.Part? = null
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

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

//        binding.btnDaftar.isEnabled = false
        binding.imageStory.setOnClickListener {
            showOptionImage()
        }

        keyName = intent.getStringExtra("name").toString()
        keyEmail = intent.getStringExtra("email").toString()
        keyTelp = intent.getStringExtra("telephone").toString()
        keyPassword = intent.getStringExtra("password").toString()
        Log.d("Debug putExtra", "$keyName, $keyEmail, $keyTelp")

        addressBundle = intent.extras!!
        latitude = addressBundle.getString("latitude").toString()
        longitude = addressBundle.getString("longitude").toString()
        address = addressBundle.getString("currentAddress").toString()


        if (!allPermissionGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        registerAction()
    }

    private fun registerAction() {
        binding.btnDaftar.setOnClickListener {
            registerAccount()
        }
        binding.btnPrev.setOnClickListener {
            val intent = Intent(this@RegisterThreeActivity, RegisterLokasiActivity::class.java)
            startActivity(intent)
        }
    }

    private fun registerAccount() {
        showLoading(true)

        binding.apply {
            viewModel.postRegister(
                keyTelp,
                keyName,
                keyEmail,
                keyPassword,
                latitude,
                longitude,
                address,
            )
        }

        viewModel.regResponse.observe(this) { response ->
            response?.let {
                if (it.error) {
                    showLoading(false)
                    showToast(it.message.toString())
                } else {
                    setupAction()
                }
            }
        }
    }

    private fun setupAction() {
        AlertDialog.Builder(this).apply {
            setTitle("Success")
            setMessage("Akun dengan $keyEmail berhasil dibuat. Login untuk melanjutkan!")
            setPositiveButton("Lanjut") { _, _ ->
                finish()
            }
            create()
            show()
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
        binding.btnDaftar.isEnabled = true
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

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun uploadFoto() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()

            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            selectedImagePart = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}