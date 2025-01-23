package com.example.dummyproject.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dummyproject.R
import com.example.dummyproject.adapters.MainListAdapter
import com.example.dummyproject.databinding.ActivityMainBinding
import com.example.dummyproject.models.AnimeListData
import com.example.dummyproject.models.MainDataList
import com.example.dummyproject.utils.ROOT
import com.example.dummyproject.utils.SendDataInterface
import com.example.dummyproject.viewmodel.AnimeViewModel

class MainActivity : AppCompatActivity(), SendDataInterface {
    private var animeListData: MainDataList? = null
    private val viewModel: AnimeViewModel by viewModels()
    private var adapter: MainListAdapter?= null
    private var binding : ActivityMainBinding?= null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initUI()
    }

    private fun initUI() {
        viewModel.hitAnimeListApi()

        viewModel.animeListData.observe(this) {
            animeListData = it
            if(animeListData?.data?.size!! > 0){
                setAdapter(animeListData?.data!!)
            }
        }

        viewModel.showLoader.observe(this){
            if(it){
                binding?.progressBar?.visibility = View.GONE
            }else{
                binding?.progressBar?.visibility = View.VISIBLE

            }
        }
    }

    private fun setAdapter(list: ArrayList<AnimeListData>) {
        adapter = MainListAdapter(this,list,this)
        binding?.animeRV?.adapter = adapter

    }

    override fun sendData(type: Int, view: Any, position: Int) {
        when(type){
            ROOT -> {
                val intent = Intent(this,DetailsActivity::class.java)
                if(!animeListData?.data.isNullOrEmpty()){
                    intent.putExtra("anime_id", animeListData?.data!![position].mal_id)
                }
                startActivity(intent)
            }
        }
    }

}