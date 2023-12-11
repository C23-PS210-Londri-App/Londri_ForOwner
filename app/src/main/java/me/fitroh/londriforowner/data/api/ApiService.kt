package me.fitroh.londriforowner.data.api

import me.fitroh.londriforowner.data.response.LoginResponse
import me.fitroh.londriforowner.data.response.ProfileResponse
import me.fitroh.londriforowner.data.response.RegisterResponse
import me.fitroh.londriforowner.data.response.ProfileResult
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @FormUrlEncoded
    @POST("auth/admin/register")
    suspend fun register(
        @Field("nomor_telepon") telp: String,
        @Field("nama_laundry") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("latitude") latitude: String,
        @Field("longitude") longitude : String,
        @Field("alamat") alamat : String,
    ): RegisterResponse

    @FormUrlEncoded
    @POST("auth/admin/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("admin/profile")
    suspend fun getProfile(@Header("Authorization") token: String): ProfileResponse
}