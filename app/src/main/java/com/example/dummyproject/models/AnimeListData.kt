package com.example.dummyproject.models

data class AnimeListData(
    val mal_id: Int,
    val images: ImagesData,
    val title: String,
    val episodes: Int?,
    val rating: String,
    val score: Double?,
)



