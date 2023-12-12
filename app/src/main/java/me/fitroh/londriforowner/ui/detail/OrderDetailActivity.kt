package me.fitroh.londriforowner.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import me.fitroh.londriforowner.R
import me.fitroh.londriforowner.databinding.ActivityOrderDetailBinding
import me.fitroh.londriforowner.models.ViewModelFactory
import me.fitroh.londriforowner.ui.home.HomeViewModel
import java.text.SimpleDateFormat
import java.util.TimeZone

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderDetailBinding
    private lateinit var orderId: String
    private lateinit var name: String
    private var berat: Int? = 0
    private var price: Int? = 0
    private lateinit var note: String
    private lateinit var status: String
    private lateinit var date: String
    private lateinit var address: String

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        orderId = intent.getStringExtra(EXTRA_ID).toString()
        name = intent.getStringExtra("extra_name").toString()
        berat = intent.getIntExtra("extra_berat", 0)
        price = intent.getIntExtra("extra_price", 0)
        note = intent.getStringExtra("extra_note").toString()
        address = intent.getStringExtra("extra_address").toString()
        status = intent.getStringExtra("extra_status").toString()
        date = intent.getStringExtra("extra_date").toString()

        getDetailOrder()
    }

    @SuppressLint("SetTextI18n")
    private fun getDetailOrder() {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val outputFormat = SimpleDateFormat("dd MMMM yyyy")
        val outputFormatTime = SimpleDateFormat("HH:mm")

        binding.apply {
            tvName.text = name
            tvAlamat.text = address
            tvNote.text = note
            tvPrice.text = "Rp $price"
            tvTotal.text = "$berat Kg"
            tvResi.text = orderId

            val parseDate = inputFormat.parse(date)
            parseDate.let { outputFormat }
            tvOrderDate.text = parseDate?.let { outputFormat.format(it) }

            val parseTime = inputFormat.parse(date)
            parseTime.let { outputFormatTime }
            tvTime.text = parseTime?.let { outputFormatTime.format(it) }
        }
        if (status == "Menunggu Diterima"){
            binding.btnProses.text = "Terima Pesanan"
            status = "Diterima"
        }else if(status == "Diterima"){
            binding.btnProses.text = "Jemput Pesanan"
        }else if(status == "Sedang Dijemput"){
            binding.btnProses.text = "Proses Pesanan"
        }else if(status == "Pesanan Sedang Diproses"){
            binding.btnProses.text = "Antar Pesanan"
        }else if(status == "Pesanan Sedang Diantar"){
            binding.btnProses.text = "Selesaikan Pesanan"
        }else{
            binding.btnProses.isEnabled = true
        }
    }

    companion object {
        var EXTRA_ID: String? = null
    }
}