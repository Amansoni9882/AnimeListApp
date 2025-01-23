package com.example.dummyproject.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dummyproject.models.AnimeDetailData
import com.example.dummyproject.models.MainDataList
import kotlinx.coroutines.launch


class AnimeViewModel : ViewModel() {

    private val _animeListData = MutableLiveData<MainDataList>()
    val animeListData: LiveData<MainDataList>
        get() = _animeListData

    private val _animeDetailData = MutableLiveData<AnimeDetailData>()
    val animeDetailData: LiveData<AnimeDetailData>
        get() = _animeDetailData

    private val _showLoader = MutableLiveData<Boolean>(false)
    val showLoader: LiveData<Boolean> get() = _showLoader

    fun hitAnimeListApi() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiInstance.getAnimeList()
                _animeListData.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }finally {
                _showLoader.postValue(true)
            }
        }
    }

    fun hitAnimeDetailsApi(malId:Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiInstance.getAnimeDetails(malId)
                _animeDetailData.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            finally {
                _showLoader.postValue(true)
            }

        }
    }
}