package me.fitroh.londriforowner.ui.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.fitroh.londriforowner.data.response.ResultAllLayananItem
import me.fitroh.londriforowner.models.UserModel
import me.fitroh.londriforowner.utils.UserRepository

class ServiceViewModel(private val repository: UserRepository) : ViewModel() {
    val isLoading : LiveData<Boolean> = repository.isLoading
    val listLayananItem : LiveData<List<ResultAllLayananItem>?> = repository.listLayananItem
    val statusServiceResponse : MutableLiveData<String?> = repository.statusServiceResponse
    val addLayananResponse : MutableLiveData<String?> = repository.addLayananResponse
    val updateInfoLayananResponse : MutableLiveData<String?> = repository.updateInfoLayananResponse

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getService(token: String) {
        viewModelScope.launch {
            repository.getService(token)
        }
    }

    fun updateStatusService(token: String, status: String, serviceId: String){
        viewModelScope.launch {
            repository.updateStatusService(token, status, serviceId)
        }
    }

    fun addInformasiService(token: String, namaLayanan: String, harga: String){
        viewModelScope.launch {
            repository.addInformasiService(token, namaLayanan, harga)
        }
    }

    fun updateInfoService(token: String, serviceId: String, namaLayanan: String, harga: String){
        viewModelScope.launch {
            repository.updateInfoService(token, serviceId, namaLayanan, harga)
        }
    }
}