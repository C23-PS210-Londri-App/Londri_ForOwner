package me.fitroh.londriforowner.data.response

import com.google.gson.annotations.SerializedName

data class DetailResponse(

	@field:SerializedName("resultOrder")
	val resultOrder: ResultOrder,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class ResultOrder(

	@field:SerializedName("layanan")
	val layanan: String,

	@field:SerializedName("latitude")
	val latitude: String,

	@field:SerializedName("catatan")
	val catatan: String,

	@field:SerializedName("estimasiBerat")
	val estimasiBerat: Int,

	@field:SerializedName("namaCustomer")
	val namaCustomer: String,

	@field:SerializedName("tanggalOrder")
	val tanggalOrder: String,

	@field:SerializedName("hargaLayanan")
	val hargaLayanan: Int,

	@field:SerializedName("hargaTotal")
	val hargaTotal: Int,

	@field:SerializedName("alamatCustomer")
	val alamatCustomer: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("orderTrx")
	val orderTrx: String,

	@field:SerializedName("longitude")
	val longitude: String,

	@field:SerializedName("status")
	val status: String
)
