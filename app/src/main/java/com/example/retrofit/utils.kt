package com.example.retrofit

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

@BindingAdapter("imageUrl")
fun getImage(view: ShapeableImageView, url: String)
{
    Glide.with(view)
        .load(url)
        .into(view)
}

//@BindingAdapter("newImage")
//fun getImage_1(view: ShapeableImageView, url: String)
//{
//    Glide.with(view)
//        .load(url)
//        .into(view)
//}