package me.fitroh.londriforowner.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import me.fitroh.londriforowner.data.api.ApiService
import me.fitroh.londriforowner.data.response.LoginResponse
import me.fitroh.londriforowner.data.response.ProfileResponse
import me.fitroh.londriforowner.data.response.RegisterResponse
import me.fitroh.londriforowner.models.UserModel
import me.fitroh.londriforowner.pref.UserPreference
import retrofit2.HttpException

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _profileResponse = MutableLiveData<ProfileResponse>()
    val profileResponse: LiveData<ProfileResponse> = _profileResponse

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

    suspend fun postRegister(
        telp: String,
        name: String,
        email: String,
        password: String,
        lat: String,
        long: String,
        alamat: String,
    ) {
        _isLoading.value = true
        try {
            val successResponse =
                apiService.register(telp, name, email, password, lat, long, alamat)
            _registerResponse.value = successResponse
            _isLoading.value = false
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
            Log.e("Error", errorResponse.toString())
            _registerResponse.value = errorResponse
        }
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

    suspend fun getProfile(token: String) {
        try {
            withContext(Dispatchers.IO) {
                val successResponse = apiService.getProfile(token)
                _profileResponse.postValue(successResponse)
                Log.d("DebugToken:", token)
                Log.d("Debug::", "$successResponse")
            }
        } catch (e: Exception) {
            val errorBody = e.message
            val errorResponseObject = Gson().fromJson(errorBody, ProfileResponse::class.java)
            Log.e("Error", errorResponseObject.toString())
            _profileResponse.value = errorResponseObject
        }
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