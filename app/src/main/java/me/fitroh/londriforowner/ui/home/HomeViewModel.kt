package me.fitroh.londriforowner.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.fitroh.londriforowner.data.response.ResultOrderItem
import me.fitroh.londriforowner.data.response.UpdateStatusLaundryResponse
import me.fitroh.londriforowner.models.UserModel
import me.fitroh.londriforowner.utils.UserRepository

class HomeViewModel(private val repository: UserRepository) : ViewModel() {
    val listOrderItemNon: LiveData<List<ResultOrderItem>> = repository.listOrderItem
    val isLoading: LiveData<Boolean> = repository.isLoading
    val updateStatusLaundryResponse: LiveData<UpdateStatusLaundryResponse> = repository.updateStatusLaundryResponse
    val statusLaundryResponse : MutableLiveData<String?> = repository.statusLaundryResponse

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getOrder(token: String){
        viewModelScope.launch {
            repository.getOrder(token)
        }
    }

    fun getStatus(token: String){
        viewModelScope.launch {
            repository.getStatus(token)
        }
    }

    fun updateStatus(token: String, status: String){
        viewModelScope.launch {
            repository.updateStatusLaundry(token, status)
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}

