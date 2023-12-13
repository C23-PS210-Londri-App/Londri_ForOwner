package me.fitroh.londriforowner.data.api

import me.fitroh.londriforowner.data.response.DetailResponse
import me.fitroh.londriforowner.data.response.HomeResponse
import me.fitroh.londriforowner.data.response.LoginResponse
import me.fitroh.londriforowner.data.response.OrderResponse
import me.fitroh.londriforowner.data.response.ProfileResponse
import me.fitroh.londriforowner.data.response.RegisterResponse
import me.fitroh.londriforowner.data.response.ProfileResult
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("auth/admin/register")
    fun register(
        @Field("nomor_telepon") telp: String,
        @Field("nama_laundry") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("latitude") latitude: String,
        @Field("longitude") longitude: String,
        @Field("alamat") alamat: String,
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("auth/admin/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("admin/profile")
    fun getProfile(
        @Header("Authorization") token: String
    )
            : Call<ProfileResponse>

    @GET("admin/laundry/order")
    fun getOrder(
        @Header("Authorization") token: String
    )
            : Call<HomeResponse>

    @FormUrlEncoded
    @POST("admin/laundry/order")
    fun postStatusOrder(
        @Header("Authorization") token: String
    ): Call<OrderResponse>

    @GET("admin/laundry/order/detail/{orderTrx}")
    fun getDetailOrder(
        @Header("Authorization") token: String,
        @Path("orderTrx") orderTrx: String
    ): Call<DetailResponse>
}