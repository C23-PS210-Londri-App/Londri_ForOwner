package me.fitroh.londriforowner.ui.service

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yagmurerdogan.toasticlib.Toastic
import me.fitroh.londriforowner.R
import me.fitroh.londriforowner.databinding.FragmentServiceAddBinding
import me.fitroh.londriforowner.models.ViewModelFactory

@Suppress("DEPRECATION")
class ServiceAddFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentServiceAddBinding
    private val viewModel by viewModels<ServiceViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private var isViewCreated = false
    private var token: String? = null

    private var onDataAddedListener: (() -> Unit)? = null

    fun setOnDataAddedListener(listener: () -> Unit) {
        onDataAddedListener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
        loadData()
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            val controller = ViewCompat.getWindowInsetsController(view)
            controller?.show(WindowInsetsCompat.Type.systemBars())
            controller?.apply {
                systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                isAppearanceLightStatusBars = true // Adjust as needed
            }

            // Let the system apply insets as normal
            insets
        }
    }

    private fun loadData() {
        binding.btnSimpan.isEnabled = false
        binding.btnSimpan.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.black_200
            )
        )
        binding.btnSimpan.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
        viewModel.getSession().observe(viewLifecycleOwner) { session ->
            token = session.token
            val tokenAuth = session.token

            binding.apply {


                edNamaLayanan.addTextChangedListener {
                    validateInput()
                }

                edHargaLayanan.addTextChangedListener {
                    validateInput()
                }

                btnSimpan.setOnClickListener {
                    val namaLayanan = binding.edNamaLayanan.text.toString()
                    val hargaLayanan = binding.edHargaLayanan.text.toString()
                    actionSimpan(tokenAuth, namaLayanan, hargaLayanan)
                }
            }
        }
    }

    private fun validateInput() {
        binding.apply {
            val isNamaLayananValid =
                edNamaLayanan.length() > 0 && edHargaLayanan.error.isNullOrEmpty()
            val isHargaLayananValid =
                edHargaLayanan.length() > 0 && edHargaLayanan.error.isNullOrEmpty()

            btnSimpan.isEnabled = isNamaLayananValid && isHargaLayananValid
            if (!btnSimpan.isEnabled) {
                btnSimpan.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black_200
                    )
                )
            } else {
                btnSimpan.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.blue_500
                    )
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServiceAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun actionSimpan(token: String, namaLayanan: String, hargaLayanan: String) {
        Log.d("DebugLayanan", "$token, $namaLayanan, $hargaLayanan")
        binding.apply {
            viewModel.addInformasiService(
                token,
                namaLayanan,
                hargaLayanan,
            )
        }

        viewModel.addLayananResponse.observe(this) { response ->
            dismiss()

            // Panggil callback ketika data berhasil ditambahkan
            onDataAddedListener?.invoke()

            // Show notification
            Toastic.toastic(
                context = requireContext(),
                message = "$response",
                duration = Toastic.LENGTH_SHORT,
                type = Toastic.SUCCESS,
                isIconAnimated = true
            ).show()
        }
    }

}