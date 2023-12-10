package me.fitroh.londriforowner.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.fitroh.londriforowner.data.response.LoginResponse
import me.fitroh.londriforowner.data.response.ProfileResponse
import me.fitroh.londriforowner.data.response.Response
import me.fitroh.londriforowner.models.UserModel
import me.fitroh.londriforowner.utils.UserRepository

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    val isLoading : LiveData<Boolean> = repository.isLoading
    val profileResponse : LiveData<Response> = repository.profileResponse

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getProfile(token: String) {
        viewModelScope.launch {
            repository.getProfile(token)
        }
    }
}