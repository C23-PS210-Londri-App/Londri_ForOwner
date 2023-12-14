package me.fitroh.londriforowner.ui.service

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.fitroh.londriforowner.data.response.ResultAllLayananItem
import me.fitroh.londriforowner.databinding.ItemServiceBinding

class ServiceAdapter(private val listServiceData: List<ResultAllLayananItem>) :
    RecyclerView.Adapter<ServiceAdapter.ListViewHolder>() {
    class ListViewHolder(private val binding: ItemServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ResultAllLayananItem) {

            binding.apply {
                tvService.text = data.namaLayanan
                tvPrice.text = "${data.hargaLayanan}"

                itemView.setOnClickListener {
                    Log.d("DebugService:", "Klik Berjalan")
                }
            }
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