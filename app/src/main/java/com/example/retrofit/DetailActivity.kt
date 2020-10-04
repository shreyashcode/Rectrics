package com.example.retrofit

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.retrofit.database.ImageDataBase
import com.example.retrofit.database.ImageEntity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val context: Context = this
        val intent: Intent = getIntent()
        var job = Job()
        var isClicked = 0
        val coroutionScope = CoroutineScope(job + Dispatchers.Main)
        coroutionScope.launch()
        {
            val list = ImageDataBase(context).getImageDao().getAllFavourite()
            for(images in list)
            {
                if(images.displayUrl.equals(intent.getStringExtra("ImageUrl")))
                {
                    likeButton.setBackgroundResource(R.drawable.like)
                    isClicked = 1
                    break
                }
            }
        }

        if(isClicked == 0)
        {
            likeButton.setBackgroundResource(R.drawable.dislike)
        }

        likeButton.setOnClickListener{

            coroutionScope.launch()
            {
                var isDeleted = 0
                val list = ImageDataBase(context).getImageDao().getAllFavourite()
                for(images in list)
                {
                    if (images.displayUrl.equals(intent.getStringExtra("ImageUrl"))) {
                        likeButton.setBackgroundResource(R.drawable.dislike)
                        ImageDataBase(context).getImageDao().delete(images)
                        isDeleted = 1
                        break
                    }
                }
                if(isDeleted == 0)
                {
                    val imageEntity = ImageEntity(1, intent.getStringExtra("DownloadUrl"), intent.getStringExtra("ImageUrl"), intent.getStringExtra("ImageAuthor"))
                    ImageDataBase(context).getImageDao().insertImage(imageEntity)
                    likeButton.setBackgroundResource(R.drawable.like)
                }
            }
        }

        download_photo.setOnClickListener(View.OnClickListener
        {
            val urlOpener: Intent = Intent(android.content.Intent.ACTION_VIEW)
            urlOpener.data = Uri.parse(intent.getStringExtra("DownloadUrl"))
            startActivity(urlOpener)
        })

        Glide.with(detail_image)
             .load(intent.getStringExtra("ImageUrl"))
             .into(detail_image)

        detail_text.text = "By: ${intent.getStringExtra("ImageAuthor")}"
    }
}