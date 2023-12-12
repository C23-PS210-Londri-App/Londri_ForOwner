package me.fitroh.londriforowner.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.fitroh.londriforowner.data.response.ResultOrderItem
import me.fitroh.londriforowner.models.UserModel
import me.fitroh.londriforowner.utils.UserRepository

class HomeViewModel(private val repository: UserRepository) : ViewModel() {
    val listOrderItemNon: LiveData<List<ResultOrderItem>> = repository.listOrderItem
    val isLoading: LiveData<Boolean> = repository.isLoading

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getOrder(token: String){
        viewModelScope.launch {
            repository.getOrder(token)
        }
    }
}

