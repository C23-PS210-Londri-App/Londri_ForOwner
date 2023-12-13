package me.fitroh.londriforowner.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.fitroh.londriforowner.data.response.ResultOrder
import me.fitroh.londriforowner.models.UserModel
import me.fitroh.londriforowner.utils.UserRepository

class DetailViewModel(private val repository : UserRepository): ViewModel() {
    val isLoading : LiveData<Boolean> = repository.isLoading
    val detailResponse : LiveData<List<ResultOrder>> = repository.orderDetailResponse

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getDetailOrder(token: String, orderTrx : String) {
        viewModelScope.launch {
            repository.getDetailOrder(token, orderTrx)
        }
    }
}