package com.example.dummyproject.models

data class AnimeMainDetails(
    val mal_id: Int,
    val url: String,
    val images: ImagesData,
    val trailer: TrailerData,
    val title: String,
    val titleEnglish: String,
    val episodes: Int?,
    val rating: String,
    val synopsis: String,
    val producers: ArrayList<GenreData> = arrayListOf(),
    val genres: ArrayList<GenreData> = arrayListOf(),
)
