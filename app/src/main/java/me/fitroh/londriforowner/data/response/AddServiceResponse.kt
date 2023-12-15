package me.fitroh.londriforowner.data.response

import com.google.gson.annotations.SerializedName

data class AddServiceResponse(

	@field:SerializedName("resultLayanan")
	val resultLayanan: ResultAddLayanan,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class ResultAddLayanan(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("namaLayanan")
	val namaLayanan: String,

	@field:SerializedName("hargaLayanan")
	val hargaLayanan: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("laundryId")
	val laundryId: Int,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
