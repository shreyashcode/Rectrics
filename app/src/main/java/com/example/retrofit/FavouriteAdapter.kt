package com.example.retrofit

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofit.database.ImageDataBase
import com.example.retrofit.databinding.FavItemViewBinding
import com.example.retrofit.databinding.ItemViewBinding
import kotlinx.android.synthetic.main.fav_item_view.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FavouriteAdapter(
    private val list: ArrayList<FavouriteImages>,
    private val listener: RecyclerViewClickListener
): RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        FavouriteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                R.layout.fav_item_view,
                parent,
                false
            )
        )

            override fun onBindViewHolder(holder: FavouriteAdapter.FavouriteViewHolder, position: Int)
            {
                Glide.with(holder.view.favImage)
                    .load(list[position].displayUrl)
                    .into(holder.view.favImage)

                holder.view.favImage.setOnClickListener{
                    listener.RecyclerViewClickListenerEntity(list[position])
                }
            }

            override fun getItemCount() = list.size

            inner class FavouriteViewHolder(
                val view: View
            ): RecyclerView.ViewHolder(view)
        }



