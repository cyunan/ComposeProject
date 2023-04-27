package com.cyn.composeproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle

class MainViewModel(private val saveStateHandle: SavedStateHandle) : BaseViewModel() {

    private val HOME_PAGE_SELECTED_INDEX = "home_page_selected_index"

    private val mSelectLiveData = MutableLiveData<Int>()

    fun getSelectedIndex(): LiveData<Int>{
        if (mSelectLiveData.value == null){
            val index = saveStateHandle.get<Int>(HOME_PAGE_SELECTED_INDEX) ?: 0
            mSelectLiveData.postValue(index)
        }
        return mSelectLiveData
    }

    fun saveSelectIndex(selectIndex: Int){
        saveStateHandle[HOME_PAGE_SELECTED_INDEX] = selectIndex
        mSelectLiveData.value = selectIndex
    }
}