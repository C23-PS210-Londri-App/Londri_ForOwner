package me.fitroh.londriforowner.data.response

import com.google.gson.annotations.SerializedName

data class AllServiceResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("resultAllLayanan")
    val resultAllLayanan: List<ResultAllLayananItem>
)

data class ResultAllLayananItem(

    @field:SerializedName("namaLayanan")
    val namaLayanan: String,

    @field:SerializedName("hargaLayanan")
    val hargaLayanan: Int,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("status")
    val status: String
)
