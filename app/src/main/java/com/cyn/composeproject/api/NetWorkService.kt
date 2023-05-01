package com.cyn.composeproject.api

import com.cyn.composeproject.api.service.MusicService
import com.cyn.composeproject.api.service.NewsService

object NetWorkService {
    private val newsService = Api.create(NewsService::class.java)

    suspend fun getNews() = newsService.getNews()

    suspend fun getMovies() = newsService.getMovies()

    private val musicService = Api.create(MusicService::class.java)

    suspend fun getMusicTopMV() = musicService.getMusicTopMV()

    suspend fun getMusicAlbum() = musicService.getMusicAlbum()
    suspend fun getMusicBanner() = musicService.getMusicBanner()
}