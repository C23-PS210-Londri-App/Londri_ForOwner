package me.fitroh.londriforowner.data.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("response")
	val response: Response? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Response(

	@field:SerializedName("fotoLaundry")
	val fotoLaundry: String? = null,

	@field:SerializedName("namaLaundry")
	val namaLaundry: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("nomorTelepon")
	val nomorTelepon: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("passwordToken")
	val passwordToken: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("namaLengkap")
	val namaLengkap: Any? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
