package com.example.retrofit

import android.view.View
import com.example.retrofit.database.ImageEntity

interface RecyclerViewClickListener
{
    fun RecyclerViewClickListenerMethod(imageData: ImageProperties, choice: Int)
    fun RecyclerViewClickListenerEntity(images: FavouriteImages)
}