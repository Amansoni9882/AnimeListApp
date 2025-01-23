package com.example.dummyproject.activities

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.dummyproject.R
import com.example.dummyproject.databinding.ActivityDetailsBinding
import com.example.dummyproject.viewmodel.AnimeViewModel
import javax.microedition.khronos.opengles.GL

class DetailsActivity : AppCompatActivity() {
    private var currentAnimeId: Int = 0
    private var binding : ActivityDetailsBinding?= null
    private val viewModel: AnimeViewModel by viewModels()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        if(intent.hasExtra("anime_id")){
            currentAnimeId = intent.getIntExtra("anime_id",0)
        }




        initUI()
    }

    private fun initUI() {
        if(currentAnimeId != 0){
            viewModel.hitAnimeDetailsApi(currentAnimeId)
        }else{
            Toast.makeText(this,
                getString(R.string.error_occured_please_try_again), Toast.LENGTH_LONG).show()
        }

        viewModel.animeDetailData.observe(this){
            if(it?.data != null){
                val genres = it.data.genres.joinToString(",") { it.name }
                val mainCast = it.data.producers.joinToString(",") { it.name }

                if(it.data.trailer != null && !it.data.trailer.embed_url.isNullOrEmpty()){
                    loadVideo(it.data.trailer.embed_url)
                    binding?.webView?.visibility = View.VISIBLE
                    binding?.imageViewIV?.visibility = View.GONE
                }else{
                    binding?.imageViewIV?.let { it1 ->
                        Glide.with(this).load(it.data.trailer.images.large_image_url).into(
                            it1
                        )
                    }
                    binding?.webView?.visibility = View.GONE
                    binding?.imageViewIV?.visibility = View.VISIBLE
                }





                binding.let { view ->
                    view?.nameTV?.text = getString(R.string.title) +it.data.title
                    view?.title?.text = it.data.title
                    view?.episodesTV?.text = getString(R.string.episodes) +it.data.episodes.toString()
                    view?.ratingTV?.text = getString(R.string.rating) +it.data.rating
                    view?.synopsisTV?.text = getString(R.string.synopsis) +it.data.synopsis
                    view?.genreTV?.text = getString(R.string.genre_s) + genres.substringAfter(",")
                    view?.castTV?.text = getString(R.string.main_cast) + mainCast.substringAfter(",")
                }

                binding?.backIV?.setOnClickListener{
                    onBackPressed()
                }
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


    fun loadVideo(youtubeId: String) {
        val video = "<iframe width=\"100%\" height=\"100%\" src=\"${youtubeId}\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>"
        binding?.webView?.loadData(video,"text/html","utf-8")
        binding?.webView?.settings?.javaScriptEnabled = true
        binding?.webView?.webChromeClient = WebChromeClient()
    }


}