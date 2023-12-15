package me.fitroh.londriforowner.data.api

import me.fitroh.londriforowner.data.response.AddServiceResponse
import me.fitroh.londriforowner.data.response.AllServiceResponse
import me.fitroh.londriforowner.data.response.DetailResponse
import me.fitroh.londriforowner.data.response.HomeResponse
import me.fitroh.londriforowner.data.response.LoginResponse
import me.fitroh.londriforowner.data.response.OrderResponse
import me.fitroh.londriforowner.data.response.ProfileResponse
import me.fitroh.londriforowner.data.response.RegisterResponse
import me.fitroh.londriforowner.data.response.ProfileResult
import me.fitroh.londriforowner.data.response.UpdateInfoServiceResponse
import me.fitroh.londriforowner.data.response.UpdateOrderResponse
import me.fitroh.londriforowner.data.response.UpdateStatusLaundryResponse
import me.fitroh.londriforowner.data.response.UpdateStatusServiceResponse
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
    ): Call<ProfileResponse>

    @GET("admin/laundry/order")
    fun getOrder(
        @Header("Authorization") token: String
    ): Call<HomeResponse>

    @GET("admin/laundry/order/detail/{orderTrx}")
    fun getDetailOrder(
        @Header("Authorization") token: String,
        @Path("orderTrx") orderTrx: String
    ): Call<DetailResponse>

    @FormUrlEncoded
    @POST("admin/laundry/order/{orderTrx}")
    fun updateOrder(
        @Header("Authorization") token: String,
        @Path("orderTrx") orderTrx: String,
        @Field("status") status: String,
    ): Call<UpdateOrderResponse>

    @GET("admin/layanan")
    fun getService(
        @Header("Authorization") token: String
    ): Call<AllServiceResponse>

    @FormUrlEncoded
    @POST("admin/status/laundry")
    fun updateStatusLaundry(
        @Header("Authorization") token: String,
        @Field("status") status: String,
    ): Call<UpdateStatusLaundryResponse>

    @FormUrlEncoded
    @POST("admin/status/layanan/{serviceId}")
    fun updateStatusService(
        @Header("Authorization") token: String,
        @Field("status") status: String,
        @Path("serviceId") serviceId: String,
    ): Call<UpdateStatusServiceResponse>

    @FormUrlEncoded
    @POST("admin/layanan/create")
    fun addLayanan(
        @Header("Authorization") token: String,
        @Field("nama_layanan") nama_layanan: String,
        @Field("harga_layanan") harga_layanan: String,
    ): Call<AddServiceResponse>

    @FormUrlEncoded
    @POST("admin/layanan/edit/{serviceId}")
    fun updateLayanan(
        @Header("Authorization") token: String,
        @Path("serviceId") serviceId: String,
        @Field("nama_layanan") nama_layanan: String,
        @Field("harga_layanan") harga_layanan: String,
    ): Call<UpdateInfoServiceResponse>
}