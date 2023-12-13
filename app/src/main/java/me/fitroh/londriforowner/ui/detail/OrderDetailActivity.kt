package me.fitroh.londriforowner.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatSpinner
import me.fitroh.londriforowner.data.dropdown.*
import me.fitroh.londriforowner.data.dropdown.DropdownAdapter
import me.fitroh.londriforowner.databinding.ActivityOrderDetailBinding
import me.fitroh.londriforowner.models.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.TimeZone

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderDetailBinding
    private lateinit var orderId: String

    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        orderId = intent.getStringExtra(EXTRA_ID).toString()

        getDetailOrder()

    }

    @SuppressLint("SetTextI18n")
    private fun getDetailOrder() {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val outputFormat = SimpleDateFormat("dd MMMM yyyy")
        val outputFormatTime = SimpleDateFormat("HH:mm")

        val spinner: AppCompatSpinner = binding.simpleSpinner
        val serviceList = ServiceListProvider.list
        val adapter = DropdownAdapter(this, serviceList)
        spinner.adapter = adapter

        viewModel.getSession().observe(this){user->
            user.token.let { token->
                viewModel.getDetailOrder(token, orderId)
                Log.d("DebugToken:" , token)
            }
        }

        viewModel.detailResponse.observe(this){profile->
            binding.apply {
                tvName.text = profile[0].namaCustomer
                tvAlamat.text = profile[0].alamatCustomer
                tvNote.text = profile[0].catatan
                tvPrice.text = "Rp ${profile[0].hargaTotal}"
                tvTotal.text = "${profile[0].estimasiBerat} Kg"
                tvResi.text = orderId
                tvService.text = profile[0].layanan

                val selectedStatus = profile[0].status
                val selectedItemIndex = serviceList.indexOfFirst { it.status == selectedStatus }

                if (selectedItemIndex != -1) {
                    spinner.setSelection(selectedItemIndex)
                }

                val parseDate = inputFormat.parse(profile[0].tanggalOrder)
                parseDate.let { outputFormat }
                tvOrderDate.text = parseDate?.let { outputFormat.format(it) }

                val parseTime = inputFormat.parse(profile[0].tanggalOrder)
                parseTime.let { outputFormatTime }
                tvTime.text = parseTime?.let { outputFormatTime.format(it) }
            }
        }

    }

    companion object {
        var EXTRA_ID: String? = null
    }
}