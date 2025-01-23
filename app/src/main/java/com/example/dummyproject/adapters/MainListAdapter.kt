package com.example.dummyproject.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dummyproject.databinding.AdapterMainListBinding
import com.example.dummyproject.models.AnimeListData
import com.example.dummyproject.utils.ROOT
import com.example.dummyproject.utils.SendDataInterface

class MainListAdapter(
    private val context: Context,
    val list: ArrayList<AnimeListData>,
    val sendDataInterface: SendDataInterface
) :
    RecyclerView.Adapter<MainListAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterMainListBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.binding.nameTV.text = "Name : ${data.title}"
        holder.binding.ratingTV.text = "Rating : ${data.rating}"
        holder.binding.episodesTV.text = "Episodes : ${data.episodes}"

        Glide.with(context).load(data.images.jpg.image_url).into(holder.binding.logoIV)

        holder.binding.root.setOnClickListener {
            sendDataInterface.sendData(ROOT,holder.binding.root,holder.adapterPosition)
        }


    }

    class ViewHolder(binding: AdapterMainListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val binding = binding

    }

}