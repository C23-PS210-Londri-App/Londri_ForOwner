package me.fitroh.londriforowner.utils

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import me.fitroh.londriforowner.data.api.ApiService
import me.fitroh.londriforowner.data.response.DetailResponse
import me.fitroh.londriforowner.data.response.HomeResponse
import me.fitroh.londriforowner.data.response.LoginResponse
import me.fitroh.londriforowner.data.response.OrderResponse
import me.fitroh.londriforowner.data.response.ProfileResponse
import me.fitroh.londriforowner.data.response.ProfileResult
import me.fitroh.londriforowner.data.response.RegisterResponse
import me.fitroh.londriforowner.data.response.ResultOrder
import me.fitroh.londriforowner.data.response.ResultOrderItem
import me.fitroh.londriforowner.data.response.ResultProses
import me.fitroh.londriforowner.models.UserModel
import me.fitroh.londriforowner.pref.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _profileResponse = MutableLiveData<List<ProfileResult>>()
    val profileResponse: LiveData<List<ProfileResult>> = _profileResponse

    private val _orderDetailResponse = MutableLiveData<List<ResultOrder>>()
    val orderDetailResponse: LiveData<List<ResultOrder>> = _orderDetailResponse

    private val _listOrderItem = MutableLiveData<List<ResultOrderItem>>()
    val listOrderItem: LiveData<List<ResultOrderItem>> = _listOrderItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun login() {
        userPreference.login()
    }

    fun postRegister(
        telp: String,
        name: String,
        email: String,
        password: String,
        lat: String,
        long: String,
        alamat: String,
    ) {
        _isLoading.value = true
        val client = apiService.register(telp, name, email, password, lat, long, alamat)

        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _registerResponse.value = response.body()
                } else {
                    Log.e(
                        TAG,
                        "ErrorMessage: ${response.message()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e(TAG, "ErrorMessage: ${t.message.toString()}")
            }
        })
    }

    suspend fun postLogin(email: String, password: String) {
        _isLoading.value = true
        try {
            val successResponse = apiService.login(email, password)
            _loginResponse.value = successResponse
            _isLoading.value = false
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            Log.e("Error", errorResponse.toString())
            _loginResponse.value = errorResponse
        }
    }

    fun getProfile(token: String) {
        val client = apiService.getProfile(token)
        client.enqueue(object : Callback<ProfileResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val userDetail = response.body()?.response
                    if (userDetail != null) {
                        _profileResponse.value = listOf(userDetail)
                    } else {
                        Log.e("Error", "onFailure: ${response.message()}")
                    }
                } else {
                    _isLoading.value = false
                    Log.e("MainViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                Log.e("MainViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getOrder(token: String) {
        _isLoading.value = true
        val client = apiService.getOrder(token)

        client.enqueue(object : Callback<HomeResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<HomeResponse>,
                response: Response<HomeResponse>
            ) {
                _isLoading.value = false
                val listData = response.body()?.resultOrder
                if (response.isSuccessful) {
                    val lengthItem = listData?.size
                    if (lengthItem != null) {
                        _listOrderItem.value = listData
                    } else {
                        Log.e(TAG, "ErrorMessage: ${response.message()}")
                    }
                } else {
                    Log.e(TAG, "ErrorMessage: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "ErrorMessage: ${t.message.toString()}")
            }
        })
    }

    fun getDetailOrder(token: String, orderTrx: String) {
        val client = apiService.getDetailOrder(token, orderTrx)
        client.enqueue(object : Callback<DetailResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val orderDetail = response.body()?.resultOrder
                    if (orderDetail != null) {
                        _orderDetailResponse.value = listOf(orderDetail)
                    } else {
                        Log.e("Error", "onFailure: ${response.message()}")
                    }
                } else {
                    _isLoading.value = false
                    Log.e("DetailViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.e("DetailViewModel", "onFailure: ${t.message}")

            }
        })
    }

    companion object {
        private const val TAG = "UserRepository"

        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}