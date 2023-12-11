package me.fitroh.londriforowner.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.fitroh.londriforowner.data.response.RegisterResponse
import me.fitroh.londriforowner.utils.UserRepository

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {
    val isLoading: LiveData<Boolean> = repository.isLoading
    val regResponse: LiveData<RegisterResponse> = repository.registerResponse

    fun postRegister(
        telp: String,
        name: String,
        email: String,
        password: String,
        latitude: String,
        longitude: String,
        alamat: String,
    ) {
        viewModelScope.launch {
            repository.postRegister(telp, name, email, password, latitude, longitude, alamat)
        }
    }
}