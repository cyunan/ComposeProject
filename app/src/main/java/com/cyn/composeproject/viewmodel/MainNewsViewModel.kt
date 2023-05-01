package com.cyn.composeproject.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyn.composeproject.api.NetWorkService
import com.cyn.composeproject.model.NewsModelModel
import com.cyn.composeproject.model.TopStoryModel
import com.cyn.composeproject.viewmodel.BaseViewModel

class MainNewsViewModel: BaseViewModel() {

    val newsLiveData = MutableLiveData<NewsModelModel>()

    fun getNewsLists() {
        launch {
            val newsModel = NetWorkService.getNews()
            //Banner占位
            newsModel.top_stories.add(0, TopStoryModel())
            newsLiveData.value = newsModel
        }
    }
}