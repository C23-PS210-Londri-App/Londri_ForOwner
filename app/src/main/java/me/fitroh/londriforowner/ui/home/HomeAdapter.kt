package me.fitroh.londriforowner.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import me.fitroh.londriforowner.data.response.ResultOrderItem
import me.fitroh.londriforowner.databinding.ItemOrderBinding
import me.fitroh.londriforowner.ui.detail.OrderDetailActivity
import me.fitroh.londriforowner.ui.detail.OrderDetailActivity.Companion.EXTRA_ID
import java.text.SimpleDateFormat
import java.util.TimeZone

class HomeAdapter (private val listOrderData: List<ResultOrderItem>) : RecyclerView.Adapter<HomeAdapter.ListViewHolder>(){
    class ListViewHolder (private val homeBinding: ItemOrderBinding): RecyclerView.ViewHolder(homeBinding.root) {

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(data: ResultOrderItem){
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val outputFormat = SimpleDateFormat("dd MMMM yyyy")
            val outputFormatTime = SimpleDateFormat("HH:mm")

            homeBinding.apply {
                noPesanan.text = data.orderTrx
                jmlBerat.text = "${data.estimasiBerat} Kg"
                totalHarga.text = "Rp ${data.hargaTotal}"
                catatan.text = data.catatan
                tvStatus.text = data.status

                val parseDate = inputFormat.parse(data.tanggalOrder)
                parseDate.let { outputFormat }
                tglPesanan.text = parseDate?.let { outputFormat.format(it) }

                val parseTime = inputFormat.parse(data.tanggalOrder)
                parseTime.let { outputFormatTime }
                jamOrder.text = parseTime?.let { outputFormatTime.format(it) }

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, OrderDetailActivity::class.java)
                    intent.putExtra(EXTRA_ID, data.orderTrx)
                    intent.putExtra("extra_berat", data.estimasiBerat)
                    intent.putExtra("extra_price", data.hargaTotal)
                    intent.putExtra("extra_note", data.catatan)
                    intent.putExtra("extra_status", data.status)
                    intent.putExtra("extra_name", data.namaCustomer)
                    intent.putExtra("extra_date", data.tanggalOrder)
                    intent.putExtra("extra_address", data.alamatCustomer)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val homeBinding =
            ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(homeBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listOrderData[position])
    }
    override fun getItemCount(): Int = listOrderData.size

}