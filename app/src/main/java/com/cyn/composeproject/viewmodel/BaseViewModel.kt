package com.cyn.composeproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyn.composeproject.ext.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

open class BaseViewModel : ViewModel() {
    val stateLiveData = MutableLiveData<State>().apply {
        value = State.Loading
    }

    fun launch(block: suspend CoroutineScope.()-> Unit){
        viewModelScope.launch {
            try {
                block()
                stateLiveData.value = State.Success
            }catch (e: Exception){
                stateLiveData.value = State.Error(e.message)
            }
        }
    }
}