package com.example.dummyproject.utils

import com.example.dummyproject.models.AnimeDetailData
import com.example.dummyproject.models.MainDataList
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("v4/top/anime")
    suspend fun getAnimeList(): MainDataList

    @GET("v4/anime/{mal_id}")
    suspend fun getAnimeDetails(
        @Path("mal_id") malId : Int
    ): AnimeDetailData
}
