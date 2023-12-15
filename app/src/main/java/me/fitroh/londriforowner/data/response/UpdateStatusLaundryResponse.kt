package me.fitroh.londriforowner.data.response

import com.google.gson.annotations.SerializedName

data class UpdateStatusLaundryResponse(

	@field:SerializedName("resultStatus")
	val resultStatus: ResultStatus,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class ResultStatus(

	@field:SerializedName("namaLaundry")
	val namaLaundry: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("status")
	val status: String
)
