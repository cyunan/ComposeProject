package com.cyn.composeproject.api.service

import com.cyn.composeproject.api.Api
import com.cyn.composeproject.model.MovieModel
import com.cyn.composeproject.model.NewsModelModel
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsService {
    @GET(Api.NEWS_URL)
    suspend fun getNews(): NewsModelModel

    @GET
    suspend fun getMovies(@Url url: String = Api.MOVIE_URL): MovieModel
}