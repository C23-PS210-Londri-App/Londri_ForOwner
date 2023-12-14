package me.fitroh.londriforowner.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatSpinner
import kotlinx.coroutines.delay
import me.fitroh.londriforowner.data.dropdown.*
import me.fitroh.londriforowner.data.dropdown.DropdownAdapter
import me.fitroh.londriforowner.databinding.ActivityOrderDetailBinding
import me.fitroh.londriforowner.models.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.TimeZone

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailBinding
    private lateinit var orderId: String

    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }

    // Data for update order
    var dataSelected = "Null"
    var dataNomorOrder = "Null"
    var dataToken = "Null"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        orderId = intent.getStringExtra(EXTRA_ID).toString()

        getDetailOrder()
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun getDetailOrder() {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val outputFormat = SimpleDateFormat("dd MMMM yyyy")
        val outputFormatTime = SimpleDateFormat("HH:mm")

        val spinner: AppCompatSpinner = binding.simpleSpinner
        val serviceList = ServiceListProvider.list
        val adapter = DropdownAdapter(this, serviceList)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val newService = serviceList[position]
                dataSelected = newService.status
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        viewModel.getSession().observe(this) { user ->
            user.token.let { token ->
                viewModel.getDetailOrder(token, orderId)
                Log.d("DebugToken:", token)

                dataToken = token
            }
        }

        viewModel.detailResponse.observe(this) { profile ->
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

                dataNomorOrder = orderId
            }
        }

        binding.btnProses.setOnClickListener {
            showLoading(true)
            Log.d("Proses", "$dataSelected, $dataNomorOrder")

            viewModel.updateOrder(dataToken, dataNomorOrder, dataSelected)

            viewModel.updateOrderResponse.observe(this) { response ->
                response?.let {
                    if (it.error) {
                        showLoading(false)
                        showToast(it.message)
                    } else {
                        showLoading(false)
                        Handler(Looper.getMainLooper()).postDelayed({
                            showToast(it.message)
                        }, 1000)
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        var EXTRA_ID: String? = null
    }
}