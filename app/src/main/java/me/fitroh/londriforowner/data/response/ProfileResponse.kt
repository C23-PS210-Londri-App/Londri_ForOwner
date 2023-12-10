package me.fitroh.londriforowner.data.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("response")
	val response: ProfileResult?,

	@field:SerializedName("error")
	val error: Boolean?,

	@field:SerializedName("message")
	val message: String?
)

data class ProfileResult(

	@field:SerializedName("fotoLaundry")
	val fotoLaundry: String?,

	@field:SerializedName("namaLaundry")
	val namaLaundry: String?,

	@field:SerializedName("latitude")
	val latitude: String?,

	@field:SerializedName("nomorTelepon")
	val nomorTelepon: String?,

	@field:SerializedName("alamat")
	val alamat: String?,

	@field:SerializedName("createdAt")
	val createdAt: String?,

	@field:SerializedName("passwordToken")
	val passwordToken: Any?,

	@field:SerializedName("id")
	val id: Int?,

	@field:SerializedName("namaLengkap")
	val namaLengkap: Any?,

	@field:SerializedName("email")
	val email: String?,

	@field:SerializedName("longitude")
	val longitude: String?,

	@field:SerializedName("status")
	val status: String?,

	@field:SerializedName("updatedAt")
	val updatedAt: String?
)
