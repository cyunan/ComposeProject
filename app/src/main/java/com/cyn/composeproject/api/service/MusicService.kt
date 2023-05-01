package com.cyn.composeproject.api.service


import com.cyn.composeproject.BannerData
import com.cyn.composeproject.api.Api
import com.cyn.composeproject.model.AlbumData
import com.cyn.composeproject.model.MVData
import retrofit2.http.GET
import retrofit2.http.Url

interface MusicService {

    @GET
    suspend fun getMusicBanner(@Url url: String = Api.MUSIC_BANNER): BannerData

    @GET
    suspend fun getMusicAlbum(@Url url: String = Api.Album): AlbumData

    @GET
    suspend fun getMusicTopMV(@Url url: String = Api.TOP_MV): MVData

    //    @GET
//    suspend fun getMusicRecommend(@Url url: String = RECOMMEND_MUSIC): RecommendData  //todo need login
}