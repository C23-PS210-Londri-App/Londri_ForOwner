package me.fitroh.londriforowner.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: Message
)

data class Message(
	val any: String? = null
)
