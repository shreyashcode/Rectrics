package com.example.retrofit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.retrofit.database.ImageDataBase
import kotlinx.android.synthetic.main.activity_favourites.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FavouritesActivity : AppCompatActivity(), RecyclerViewClickListener
{
    private lateinit var list_1: ArrayList<FavouriteImages>
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)

        val context: Context = this
        var job = Job()
        val coroutionScope = CoroutineScope(job + Dispatchers.Main)
        list_1 = ArrayList()
        coroutionScope.launch()
        {
            val list = ImageDataBase(context).getImageDao().getAllFavourite()
            for(images in list)
            {
                val favouriteImage: FavouriteImages = FavouriteImages(images.downLoadUrl, images.displayUrl, images.author)
                list_1.add(favouriteImage)
            }
            loadData()
        }
    }

    
    private fun loadData()
    {
        recyclerView2.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView2.setHasFixedSize(true)
        recyclerView2.adapter = FavouriteAdapter(list_1, this)
    }

    override fun RecyclerViewClickListenerMethod(imageData: ImageProperties, choice: Int)
    {

    }

    override fun RecyclerViewClickListenerEntity(images: FavouriteImages)
    {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("ImageUrl", images.displayUrl)
        intent.putExtra("DownloadUrl", images.downLoadUrl)
        intent.putExtra("ImageAuthor", images.author)
        startActivity(intent)
    }

}