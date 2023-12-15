package me.fitroh.londriforowner.ui.service

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import me.fitroh.londriforowner.R
import me.fitroh.londriforowner.data.response.ResultAllLayananItem
import me.fitroh.londriforowner.databinding.ItemServiceBinding

class ServiceAdapter(
    private val listServiceData: List<ResultAllLayananItem>,
    private val viewModel: ServiceViewModel,
    private val tokenAuth: String,
) :
    RecyclerView.Adapter<ServiceAdapter.ListViewHolder>() {
    inner class ListViewHolder(private val binding: ItemServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(data: ResultAllLayananItem) {
            binding.apply {
                tvService.text = data.namaLayanan
                tvPrice.text = "${data.hargaLayanan}/kg"

                if(data.status == "Tersedia"){
                    statsLayanan.setBackgroundResource(R.color.colorGreen)
                    tvStatsLayanan.setText(R.string.tersedia)
                }else{
                    statsLayanan.setBackgroundResource(R.color.black_300)
                    tvStatsLayanan.setText(R.string.tidak_tersedia)
                }

                val serviceId = data.id
                val namaLayanan = data.namaLayanan
                val hargaLayanan = data.hargaLayanan

                statsLayanan.setOnClickListener {
                    val status = if (data.status == "Tersedia") "Tidak Tersedia" else "Tersedia"

                    Log.d("PostStatus", status)
                    if(status == "Tersedia"){
                        statsLayanan.setBackgroundResource(R.color.colorGreen)
                        tvStatsLayanan.setText(R.string.tersedia)
                    }else{
                        statsLayanan.setBackgroundResource(R.color.black_300)
                        tvStatsLayanan.setText(R.string.tidak_tersedia)
                    }
                    postStatusToApi(tokenAuth, status, serviceId.toString())

                }

                itemView.setOnClickListener {
                    // Alert Dialog Here
                    showUpdateLayanan(serviceId ,namaLayanan, hargaLayanan)
                }
            }
        }

        private fun showUpdateLayanan(serviceId: Int, nama: String, harga: Int) {
            val builder = AlertDialog.Builder(itemView.context)
            val inflater = LayoutInflater.from(itemView.context)
            val view = inflater.inflate(R.layout.fragment_service_add, null)

            val namaLayanan = view.findViewById<TextInputEditText>(R.id.edNamaLayanan)
            val hargaLayanan = view.findViewById<TextInputEditText>(R.id.edHargaLayanan)
            val btnSimpan = view.findViewById<Button>(R.id.btnSimpan)

            namaLayanan.setText(nama)
            hargaLayanan.setText(harga.toString())

            builder.setView(view)
            val dialog = builder.create()
            dialog.show()

            btnSimpan.setOnClickListener {
                Log.e("PopUp", "$namaLayanan, $hargaLayanan")
                updateInfoToApi(tokenAuth, serviceId.toString(), namaLayanan.text.toString(), hargaLayanan.text.toString())
                dialog.dismiss()
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        private fun updateInfoToApi(token: String, serviceId: String, nama: String, harga: String) {
            binding.apply {
                viewModel.updateInfoService(token, serviceId, nama, harga)
            }
            notifyDataSetChanged()
        }

        @SuppressLint("NotifyDataSetChanged")
        private fun postStatusToApi(token: String, status: String, serviceId: String) {
            binding.apply {
                viewModel.updateStatusService(token, status, serviceId)
            }
            notifyDataSetChanged()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listServiceData[position])
    }

    override fun getItemCount(): Int = listServiceData.size


}