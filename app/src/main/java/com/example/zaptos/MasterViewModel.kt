package com.example.zaptos

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.docode.Repository.MainRepository
import com.example.zaptos.models.ItemModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MasterViewModel(
    job: Job
) : ViewModel() {

    companion object {
        var myurl : String = "https://www.google.com"
    }

    private val myJob = job

    private val _nikeList = MutableLiveData(mutableStateOf(mutableStateListOf<ItemModel>()))
    val nikeList: MutableState<SnapshotStateList<ItemModel>>? = _nikeList.value
    fun getNikeData(context: Context, userMainRepository: MainRepository, job: Job) {
        viewModelScope.launch(job + Dispatchers.Main) {
            viewModelScope.launch(Dispatchers.IO) {
                userMainRepository.getNikeItems(context, _nikeList)
            }
        }
    }

    private val _adidasList = MutableLiveData(mutableStateOf(mutableStateListOf<ItemModel>()))
    val adidasList: MutableState<SnapshotStateList<ItemModel>>? = _adidasList.value
    fun getAdidasData(context: Context, userMainRepository: MainRepository, job: Job) {
        viewModelScope.launch(job + Dispatchers.Main) {
            viewModelScope.launch(Dispatchers.IO) {
                userMainRepository.getAdidasItems(context, _adidasList)
            }
        }
    }

    private val _pumaList = MutableLiveData(mutableStateOf(mutableStateListOf<ItemModel>()))
    val pumaList: MutableState<SnapshotStateList<ItemModel>>? = _pumaList.value
    fun getPumaData(context: Context, userMainRepository: MainRepository, job: Job) {
        viewModelScope.launch(job + Dispatchers.Main) {
            viewModelScope.launch(Dispatchers.IO) {
                userMainRepository.getPumaItems(context, _pumaList)
            }
        }
    }

    private val _paymentLink = MutableLiveData(mutableStateOf("https://www.google.com"))
    val paymentLink: MutableState<String>? = _paymentLink.value
    fun getPaymentLink(context: Context, userMainRepository: MainRepository, job: Job, cart_id: String) {
        viewModelScope.launch(job + Dispatchers.Main) {
            viewModelScope.launch(Dispatchers.IO) {
                userMainRepository.getPaymentLink(context, _paymentLink, cart_id)
            }
        }
    }

    // Authenticated User
    val currentUser = Firebase.auth.currentUser

    // Auth Object
    val auth = Firebase.auth

    override fun onCleared() {
        super.onCleared()
        myJob.cancel()
    }

}