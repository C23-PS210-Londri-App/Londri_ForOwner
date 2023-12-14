package me.fitroh.londriforowner.data.response

import com.google.gson.annotations.SerializedName

data class UpdateOrderResponse(

    @field:SerializedName("resultProses")
    val resultUpdateOrder: ResultUpdateOrder,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class ResultUpdateOrder(

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("namaLayanan")
    val namaLayanan: String,

    @field:SerializedName("hargaTotal")
    val hargaTotal: Int,

    @field:SerializedName("namaLaundry")
    val namaLaundry: String,

    @field:SerializedName("customerId")
    val customerId: Int,

    @field:SerializedName("catatan")
    val catatan: String,

    @field:SerializedName("estimasiBerat")
    val estimasiBerat: Int,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("orderTrx")
    val orderTrx: String,

    @field:SerializedName("namaCustomer")
    val namaCustomer: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)
